package com.greenfoxacademy.goddesstribesbackend.models.dtos;

import java.util.List;

public class ResourcesDTO {

  private List<ResourceDTO> resources;

  public ResourcesDTO() {
  }

  public ResourcesDTO(List<ResourceDTO> resources) {
    this.resources = resources;
  }

  public List<ResourceDTO> getResources() {
    return resources;
  }

  public void setResources(List<ResourceDTO> resources) {
    this.resources = resources;
  }

}
