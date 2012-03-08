/*
 * Copyright (c) 2012, Metron, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Metron, Inc. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL METRON, INC. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3-
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2011.12.05 at 11:09:09 AM EST
//


package com.metsci.glimpse.dspl.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 *
 *         A concept is a definition of a type of data that appears in the
 *         dataset (e.g., "GDP" or "County").  A concept may be associated with
 *         an enumeration of all its possible values or not. A concept defined in
 *         some dataset may be referenced in other datasets.
 *
 *
 * <p>Java class for Concept complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Concept">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="info" type="{http://schemas.google.com/dspl/2010}ConceptInfo"/>
 *         &lt;element name="topic" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="type" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ref" use="required" type="{http://schemas.google.com/dspl/2010}DataType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="attribute" type="{http://schemas.google.com/dspl/2010}Attribute" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="property" type="{http://schemas.google.com/dspl/2010}ConceptProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="defaultValue" type="{http://schemas.google.com/dspl/2010}Value" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="table" type="{http://schemas.google.com/dspl/2010}ConceptTableMapping" minOccurs="0"/>
 *           &lt;element name="data" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="table" type="{http://schemas.google.com/dspl/2010}ConceptTableMapping"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://schemas.google.com/dspl/2010}Id" />
 *       &lt;attribute name="extends" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Concept", propOrder = {
    "info",
    "topic",
    "type",
    "attribute",
    "property",
    "defaultValue",
    "tableMapping",
    "data"
})
public class Concept {

    @XmlElement(required = true)
    protected ConceptInfo info;
    protected List<Concept.Topic> topic;
    protected Concept.Type type;
    protected List<Attribute> attribute;
    protected List<ConceptProperty> property;
    protected Value defaultValue;
    @XmlElement(name = "table")
    protected ConceptTableMapping tableMapping;
    protected Concept.Data data;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    @XmlAttribute(name = "extends")
    protected QName _extends;

    /**
     * Gets the value of the info property.
     *
     * @return
     *     possible object is
     *     {@link ConceptInfo }
     *
     */
    public ConceptInfo getInfo() {
        return info;
    }

    /**
     * Sets the value of the info property.
     *
     * @param value
     *     allowed object is
     *     {@link ConceptInfo }
     *
     */
    public void setInfo(ConceptInfo value) {
        this.info = value;
    }

    /**
     * Gets the value of the topic property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the topic property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTopic().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Concept.Topic }
     *
     *
     */
    public List<Concept.Topic> getTopic() {
        if (topic == null) {
            topic = new ArrayList<Concept.Topic>();
        }
        return this.topic;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link Concept.Type }
     *
     */
    public Concept.Type getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link Concept.Type }
     *
     */
    public void setType(Concept.Type value) {
        this.type = value;
    }

    /**
     * Gets the value of the attribute property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attribute property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attribute }
     *
     *
     */
    public List<Attribute> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<Attribute>();
        }
        return this.attribute;
    }

    /**
     * Gets the value of the property property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConceptProperty }
     *
     *
     */
    public List<ConceptProperty> getProperty() {
        if (property == null) {
            property = new ArrayList<ConceptProperty>();
        }
        return this.property;
    }

    /**
     * Gets the value of the defaultValue property.
     *
     * @return
     *     possible object is
     *     {@link Value }
     *
     */
    public Value getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     *
     * @param value
     *     allowed object is
     *     {@link Value }
     *
     */
    public void setDefaultValue(Value value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the tableMapping property.
     *
     * @return
     *     possible object is
     *     {@link ConceptTableMapping }
     *
     */
    public ConceptTableMapping getTableMapping() {
        return tableMapping;
    }

    /**
     * Sets the value of the tableMapping property.
     *
     * @param value
     *     allowed object is
     *     {@link ConceptTableMapping }
     *
     */
    public void setTableMapping(ConceptTableMapping value) {
        this.tableMapping = value;
    }

    /**
     * Gets the value of the data property.
     *
     * @return
     *     possible object is
     *     {@link Concept.Data }
     *
     */
    public Concept.Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     *
     * @param value
     *     allowed object is
     *     {@link Concept.Data }
     *
     */
    public void setData(Concept.Data value) {
        this.data = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the extends property.
     *
     * @return
     *     possible object is
     *     {@link QName }
     *
     */
    public QName getExtends() {
        return _extends;
    }

    /**
     * Sets the value of the extends property.
     *
     * @param value
     *     allowed object is
     *     {@link QName }
     *
     */
    public void setExtends(QName value) {
        this._extends = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="table" type="{http://schemas.google.com/dspl/2010}ConceptTableMapping"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "table"
    })
    public static class Data {

        @XmlElement(required = true)
        protected ConceptTableMapping table;

        /**
         * Gets the value of the table property.
         *
         * @return
         *     possible object is
         *     {@link ConceptTableMapping }
         *
         */
        public ConceptTableMapping getTable() {
            return table;
        }

        /**
         * Sets the value of the table property.
         *
         * @param value
         *     allowed object is
         *     {@link ConceptTableMapping }
         *
         */
        public void setTable(ConceptTableMapping value) {
            this.table = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}QName" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Topic {

        @XmlAttribute(name = "ref")
        protected QName ref;

        /**
         * Gets the value of the ref property.
         *
         * @return
         *     possible object is
         *     {@link QName }
         *
         */
        public QName getRef() {
            return ref;
        }

        /**
         * Sets the value of the ref property.
         *
         * @param value
         *     allowed object is
         *     {@link QName }
         *
         */
        public void setRef(QName value) {
            this.ref = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="ref" use="required" type="{http://schemas.google.com/dspl/2010}DataType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Type {

        @XmlAttribute(name = "ref", required = true)
        protected DataType ref;

        /**
         * Gets the value of the ref property.
         *
         * @return
         *     possible object is
         *     {@link DataType }
         *
         */
        public DataType getRef() {
            return ref;
        }

        /**
         * Sets the value of the ref property.
         *
         * @param value
         *     allowed object is
         *     {@link DataType }
         *
         */
        public void setRef(DataType value) {
            this.ref = value;
        }

    }


    @javax.xml.bind.annotation.XmlTransient
    protected com.metsci.glimpse.dspl.parser.table.PropertyTableData tableData;

    @javax.xml.bind.annotation.XmlTransient
    protected DataSet parentDataset;

    public DataSet getDataSet( )
    {
        return parentDataset;
    }

    public void setDataSet( DataSet dataSet )
    {
        this.parentDataset = dataSet;
    }

    @javax.xml.bind.annotation.XmlTransient
    protected Concept parentConcept;

    public Concept getParentConcept( )
    {
        return parentConcept;
    }

    public void setParentConcept( Concept parent )
    {
        this.parentConcept = parent;
    }

    public String getNameEnglish( )
    {
        if ( info != null )
        {
            String englishName = info.getNameEnglish( );

            if ( englishName != null )
                return englishName;
        }

        // if no name is provided use the id
        return id;
    }

    public Table getTable( ) throws javax.xml.bind.JAXBException, java.io.IOException, com.metsci.glimpse.dspl.util.DsplException
    {
        return com.metsci.glimpse.dspl.util.DsplHelper.getTable( this );
    }

    public com.metsci.glimpse.dspl.parser.table.PropertyTableData getTableData( ) throws javax.xml.bind.JAXBException, java.io.IOException, com.metsci.glimpse.dspl.util.DsplException
    {
        if ( tableData != null )
            return tableData;

        tableData = com.metsci.glimpse.dspl.util.DsplHelper.getTableData( this );

        return tableData;
    }

    public boolean isInstanceOf( Concept superConcept )
    {
        return com.metsci.glimpse.dspl.util.DsplHelper.isInstanceOf( this, superConcept );
    }

    public ConceptProperty getProperty( String ref )
    {
        if ( ref == null )
            return null;

        for( ConceptProperty property : getProperty( ) )
        {
            if ( ref.equals( property.getId( ) ) )
            {
                return property;
            }
        }

        return null;
    }

    public Attribute getAttribute( String ref )
    {
        if ( ref == null )
            return null;

        for( Attribute attribute : getAttribute( ) )
        {
            if ( ref.equals( attribute.getId( ) ) )
            {
                return attribute;
            }
        }

        return null;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o == null ) return false;
        if ( o == this ) return true;
        if ( o instanceof Concept )
        {
            Concept c = (Concept) o;
            return com.metsci.glimpse.dspl.util.DsplHelper.equals( this, c );
        }
        return false;
    }


}
