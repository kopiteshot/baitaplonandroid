package com.example.kopiteshot.buoi15parserxml.control.xml;

import com.example.kopiteshot.buoi15parserxml.model.ItemNew;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Kopiteshot on 5/14/2017.
 */

public class ParseXML extends DefaultHandler {
    public static final String ITEM = "item";
    public static final String DESCRIPTION = "description";
    public static final String LINK = "link";
    public static final String PUBDATE = "pubDate";
    public static final String TITLE = "title";
    public static final String GUID = "guid";

    private StringBuilder stringBuilder;
    private ArrayList<ItemNew> itemNews = new ArrayList<>();
    private ItemNew itemNew = new ItemNew("", "", "", "", "", "", "");
    private StringBuilder htmlcode;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals(ITEM)) {
            itemNew = new ItemNew("", "", "", "", "", "", "");
        }
        stringBuilder = new StringBuilder();

    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        stringBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals(TITLE)) {
            itemNew.setTitle(stringBuilder.toString());
        } else if (qName.equals(LINK)) {
            itemNew.setLink(stringBuilder.toString());
        } else if (qName.equals(DESCRIPTION)) {
            itemNew.setDescription(stringBuilder.toString());
            itemNew.setLink(getMyLink(stringBuilder).toString());
            StringBuilder link = getMyImage(stringBuilder);
            itemNew.setImage(getMyImage(stringBuilder).toString());
            itemNew.setHtmlcode("");
        } else if (qName.equals(ITEM)) {
            itemNews.add(itemNew);
        } else if (qName.equals(PUBDATE)) {
            itemNew.setPubDate(stringBuilder.toString());
        } else if (qName.equals(GUID)) {
            itemNew.setGuid(stringBuilder.toString());
        }
    }

    public ArrayList<ItemNew> getArr() {
        return itemNews;
    }

    private StringBuilder getMyLink(StringBuilder s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length() - 4; i++) {
            if (s.charAt(i) == 'u' && s.charAt(i + 1) == 'r' && s.charAt(i + 2) == 'l' && s.charAt(i + 3) == '=') {
                int j = i + 4;
                while (s.charAt(j) != '"') {
                    stringBuilder.append(s.charAt(j));
                    j++;
                }
                break;
            }

        }
        return stringBuilder;
    }

    private StringBuilder getMyImage(StringBuilder s) {
        StringBuilder tmp = new StringBuilder();
        tmp.append("http://");
        for (int i = 0; i < s.length() - 6; i++) {
            if (s.charAt(i) == 'i' && s.charAt(i + 1) == 'm' && s.charAt(i + 2) == 'a' && s.charAt(i + 3) == 'g' && s.charAt(i + 4) == 'e' && s.charAt(i + 5) == 's') {
                for (int k = i - 15; k < i; k++) {
                    tmp.append(s.charAt(k));
                }
                tmp.append("images");
                int j = i + 6;
                while (s.charAt(j) != '"') {
                    tmp.append(s.charAt(j));
                    j++;
                }
                break;
            }
        }
        return tmp;
    }


}
