package org.smr.common.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {
    public XMLUtil() {
    }

    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);
        return documentBuilderFactory.newDocumentBuilder();
    }

    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }

    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap();
            DocumentBuilder documentBuilder = newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for(int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == 1) {
                    Element element = (Element)node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }

            try {
                stream.close();
            } catch (Exception var9) {
                ;
            }

            return data;
        } catch (Exception var10) {
            throw var10;
        }
    }

    public static String mapToXml(Map<String, String> data) throws Exception {
        Document document = newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        Iterator var3 = data.keySet().iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            String value = (String)data.get(key);
            if (value == null) {
                value = "";
            }

            value = value.trim();
            Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty("indent", "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();

        try {
            writer.close();
        } catch (Exception var10) {
            ;
        }

        return output;
    }

    public static String setWechatXml(String return_code, String return_msg) {
        SortedMap<String, String> parameters = new TreeMap();
        parameters.put("return_code", return_code);
        parameters.put("return_msg", return_msg);
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

    public static Map aliPayNotify(HttpServletRequest request) {
        Map<String, String> map = new HashMap();
        Map requestParams = request.getParameterMap();
        Iterator iter = requestParams.keySet().iterator();

        while(iter.hasNext()) {
            String name = (String)iter.next();
            String[] values = (String[])((String[])requestParams.get(name));
            String valueStr = "";

            for(int i = 0; i < values.length; ++i) {
                valueStr = i == values.length - 1 ? valueStr + values[i] : valueStr + values[i] + ",";
            }

            map.put(name, valueStr);
        }

        try {
            return map;
        } catch (Exception var8) {
            throw new RuntimeException("解析失败", var8);
        }
    }

    public static Map wxPayNotify(HttpServletRequest request) {
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            boolean var4 = false;

            int len;
            while((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");
            return xmlToMap(result);
        } catch (Exception var6) {
            throw new RuntimeException("解析失败", var6);
        }
    }

    public static List<String> getLikeByMap(Map<String, String> map, String keyLike) {
        List<String> list = new Vector();
        Iterator var3 = map.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, String> entity = (Entry)var3.next();
            if (((String)entity.getKey()).indexOf(keyLike) > -1) {
                list.add(entity.getValue());
            }
        }

        return list;
    }
}
