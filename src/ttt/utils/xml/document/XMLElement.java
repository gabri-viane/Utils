/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import ttt.utils.xml.engine.interfaces.IXMLElement;
import ttt.utils.xml.engine.interfaces.IXMLTag;

/**
 * Elemento base di un file XML. Può contenere zero o molteplici tags
 * ({@link IXMLTag}) e può avere anche altri sotto-elementi
 * ({@link IXMLElement}).
 *
 * @author TTT
 */
public class XMLElement implements IXMLElement {

    private final String name;
    private String value;
    private final HashMap<String, IXMLTag> tags = new HashMap<>();
    private final ArrayList<IXMLElement> sub_elements = new ArrayList<>();

    /**
     * Crea un nuovo elemento con tutti i metodi cià implementati. Evita di
     * dover implementarli ogni volta.
     *
     * @param name
     */
    public XMLElement(String name) {
        this.name = name;
    }

    @Override
    public boolean hasSubElements() {
        return sub_elements.size() > 0;
    }

    @Override
    public void addSubElement(IXMLElement element) {
        sub_elements.add(element);
    }

    @Override
    public void removeSubElement(IXMLElement element) {
        sub_elements.remove(element);
    }

    @Override
    public boolean hasTag(String name) {
        return tags.containsKey(name);
    }

    @Override
    public IXMLTag getTag(String name) {
        return tags.get(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean hasElement(IXMLElement element) {
        return sub_elements.contains(element);
    }

    @Override
    public void addTag(IXMLTag tag) {
        tags.put(tag.getName(), tag);
    }

    @Override
    public List<IXMLTag> getTags() {
        ArrayList<IXMLTag> tgs = new ArrayList<>();
        tgs.addAll(tags.values());
        return Collections.unmodifiableList(tgs);
    }

    @Override
    public List<IXMLElement> getElements() {
        return Collections.unmodifiableList(sub_elements);
    }

    @Override
    public IXMLElement getFirstElement(String name) {
        return sub_elements.stream().filter((t) -> {
            return t.getName().equals(name);
        }).findFirst().orElse(null);
    }

    private boolean closed = false;

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public IXMLElement getLast() {
        if (!closed) {
            if (hasSubElements()) {
                IXMLElement last = sub_elements.get(sub_elements.size() - 1).getLast();
                if (last != null) {
                    return last;
                }
            }
            return this;
        }
        return null;
    }

}
