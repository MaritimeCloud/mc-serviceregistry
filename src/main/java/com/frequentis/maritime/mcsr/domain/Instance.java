/*
 * MaritimeCloud Service Registry
 * Copyright (c) 2016 Frequentis AG
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.frequentis.maritime.mcsr.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.frequentis.maritime.mcsr.domain.util.JsonNodeConverter;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Holds a description of an service instance.An instance can be compatible to one or morespecification templates.It has at least a technical representation of thedescriptiion in form of an XML and a filled out templateas e.g. word document.
 *
 */
@ApiModel(description = ""
    + "Holds a description of an service instance.An instance can be compatible to one or morespecification templates.It has at least a technical representation of thedescriptiion in form of an XML and a filled out templateas e.g. word document."
    + "")
@Entity
@Table(name = "instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instance")
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "geometry", columnDefinition = "LONGTEXT")
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode geometry;

    @Column(name = "geometry_content_type")
    private String geometryContentType;

    @NotNull
    @Column(name = "instance_id", nullable = false)
    private String instanceId;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "status")
    private String status;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "unlocode")
    private String unlocode;

    @Column(name = "endpoint_uri")
    private String endpointUri;

    @Column(name = "endpoint_type")
    private String endpointType;

    @OneToOne
    @JoinColumn(unique = true)
    private Xml instanceAsXml;

    @OneToOne
    @JoinColumn(unique = true)
    private Doc instanceAsDoc;

    @ManyToOne
    private SpecificationTemplate implementedSpecificationVersion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "instance_designs",
               joinColumns = @JoinColumn(name="instances_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="designs_id", referencedColumnName="ID"))
    private Set<Design> designs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "instance_docs",
               joinColumns = @JoinColumn(name="instances_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="docs_id", referencedColumnName="ID"))
    private Set<Doc> docs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public JsonNode getGeometry() {
        return geometry;
    }

    public void setGeometry(JsonNode geometry) {
        this.geometry = geometry;
    }

    public String getGeometryContentType() {
        return geometryContentType;
    }

    public void setGeometryContentType(String geometryContentType) {
        this.geometryContentType = geometryContentType;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUnlocode() {
        return unlocode;
    }

    public void setUnlocode(String unlocode) {
        this.unlocode = unlocode;
    }

    public String getEndpointUri() {
        return endpointUri;
    }

    public void setEndpointUri(String endpointUri) {
        this.endpointUri = endpointUri;
    }

    public String getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(String endpointType) {
        this.endpointType = endpointType;
    }

    public Xml getInstanceAsXml() {
        return instanceAsXml;
    }

    public void setInstanceAsXml(Xml xml) {
        this.instanceAsXml = xml;
    }

    public Doc getInstanceAsDoc() {
        return instanceAsDoc;
    }

    public void setInstanceAsDoc(Doc doc) {
        this.instanceAsDoc = doc;
    }

    public SpecificationTemplate getImplementedSpecificationVersion() {
        return implementedSpecificationVersion;
    }

    public void setImplementedSpecificationVersion(SpecificationTemplate specificationTemplate) {
        this.implementedSpecificationVersion = specificationTemplate;
    }

    public Set<Design> getDesigns() {
        return designs;
    }

    public void setDesigns(Set<Design> designs) {
        this.designs = designs;
    }

    public Set<Doc> getDocs() {
        return docs;
    }

    public void setDocs(Set<Doc> docs) {
        this.docs = docs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Instance instance = (Instance) o;
        if(instance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, instance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Instance{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", version='" + version + "'" +
            ", comment='" + comment + "'" +
            ", geometry='" + geometry + "'" +
            ", geometryContentType='" + geometryContentType + "'" +
            ", instanceId='" + instanceId + "'" +
            ", keywords='" + keywords + "'" +
            ", status='" + status + "'" +
            ", organizationId='" + organizationId + "'" +
            ", unlocode='" + unlocode + "'" +
            ", endpointUri='" + endpointUri + "'" +
            ", endpointType='" + endpointType + "'" +
            '}';
    }
}
