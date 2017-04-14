package br.com.emersonluiz.validator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.UrlValidator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.com.emersonluiz.exception.FailureException;

public class Validator {

    public boolean validate(FileReader schema, FileReader json) throws FailureException {

        JSONParser parser = new JSONParser();

        try {

            // Schema
            Object obj1 = parser.parse(schema);
            JSONObject jsonObject1 = (JSONObject) obj1;
            JSONArray objSchema = (JSONArray) jsonObject1.get("schema");

            // Data
            Object objData = parser.parse(json);

            if (objData.toString().indexOf("[") == 0) {
                JSONArray jsonObject = (JSONArray) objData;
                verifyObjects(objSchema, jsonObject);
            } else {
                JSONObject jsonObject = (JSONObject) objData;
                verifyObject(objSchema, jsonObject);
            }

        } catch (IOException e) {
            throw new FailureException("IO Error");
        } catch (ParseException e) {
            throw new FailureException("Json Parse Error");
        }

        return true;
    }

    private void verifyObjects(JSONArray schema, JSONArray data) throws FailureException {
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> iterator = data.iterator();
        while (iterator.hasNext()) {
            JSONObject ob = iterator.next();
            verifyObject(schema, ob);
        }
    }

    private void verifyObject(JSONArray schema, JSONObject data) throws FailureException {
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> iterator = schema.iterator();
        while (iterator.hasNext()) {

            JSONObject ob = iterator.next();

            // verify mixed
            if (ob.get("type").equals("mixed")) {
                if (data.get(ob.get("name")).toString().indexOf("[") != -1) {
                    verifyArray(data, ob);
                } else {
                    verifyString(data, ob);
                }
                continue;
            }

            // verify array
            if (ob.get("type").equals("array")) {
                if (data.get(ob.get("name")).toString().indexOf("[") == -1) {
                    throw new FailureException("Field '" + ob.get("name") + "' is invalid! ("+ data.get(ob.get("name")).toString() + ")");
                }
                verifyArray(data, ob);

                continue;
            }

            // verify string
            verifyString(data, ob);

            // verify image
            verifyImageExtension(ob, data);

            // verify double
            if (ob.get("type").equals("double")) {
                verifyDouble(data, ob);
            }
        }
    }

    private boolean verifyDouble(JSONObject data, JSONObject schema) throws FailureException {
        String value = (String) data.get(schema.get("name"));

        if (value == null) {
            return false;
        }

        Matcher matcher = getMatcher("^[0-9]{1,}\\.\\d{2,2}", value);
        if (matcher.find()) {
            return false;
        }

        matcher = getMatcher("^[0-9]{1,}$", value);
        if (!matcher.find()) {
            throw new FailureException("Field '" + schema.get("name") + "' is invalid! (" + data.get(schema.get("name")).toString() + ")");
        }

        return true;
    }

    private Matcher getMatcher(String re, String value) {
        Pattern pattern = Pattern.compile(re);
        return pattern.matcher(value);
    }

    private boolean verifyArray(JSONObject data, JSONObject schema) throws FailureException {
        if (!exists(schema, "required")) {
            return false;
        }

        JSONArray node = (JSONArray) data.get(schema.get("name"));
        if (node == null) {
            throw new FailureException("Field '" + schema.get("name") + "' is required! (" + data.get(schema.get("name")).toString() + ")");
        }
        if (node.size() < 1) {
            throw new FailureException("Field '" + schema.get("name") + "' is required! (" + data.get(schema.get("name")).toString() + ")");
        }
        return true;
    }

    private boolean verifyString(JSONObject data, JSONObject schema) throws FailureException {
        if (!exists(schema, "required")) {
            return false;
        }

        String value = (String) data.get(schema.get("name"));
        if (value == null) {
            throw new FailureException("Field '" + schema.get("name") + "' is required!");
        }
        if (value.trim().equals("")) {
            throw new FailureException("Field '" + schema.get("name") + "' is required!");
        }
        return true;
    }

    private boolean verifyImageExtension(JSONObject schema, JSONObject data) throws FailureException {
        if (!exists(schema, "image")) {
            return false;
        }

        if (data.get(schema.get("name")) == null) {
            return false;
        }

        String text = data.get(schema.get("name")).toString();
        if (text != null) {
            validateImage(schema.get("name").toString(), data.get(schema.get("name")).toString());
            validateURL(data.get(schema.get("name")).toString());
        }

        return true;
    }

    private boolean exists(JSONObject object, String property) {
        if (object.get(property) == null) {
            return false;
        }
        return (Boolean) object.get(property);
    }

    private void validateImage(String field, String value) throws FailureException {
        String extensions = ".jpg|.jpeg|.png|.gif";

        if (value.indexOf(".") == -1) {
            throw new FailureException("Field '" + field + "' is invalid! (" + value + ")");
        }

        String extension = value.substring(value.lastIndexOf("."), value.length());
        if (!extensions.contains(extension)) {
            throw new FailureException("Field '" + field + "' is invalid! (" + value + ")");
        }

    }

    private void validateURL(String url) throws FailureException {
        String[] schemes = { "http", "https" };
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(url)) {
            throw new FailureException("URL is invalid! (" + url + ")");
        }
    }

}