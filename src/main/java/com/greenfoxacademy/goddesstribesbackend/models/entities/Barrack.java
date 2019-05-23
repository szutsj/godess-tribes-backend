package com.greenfoxacademy.goddesstribesbackend.models.entities;

import com.greenfoxacademy.goddesstribesbackend.models.dtos.BuildingTypeENUM;

import javax.persistence.Entity;

@Entity
public class Barrack extends Building {

  public Barrack() {
  }

  public Barrack(Kingdom kingdom) {
    super(kingdom);
    this.setBuildingTypeENUM(BuildingTypeENUM.BARRACK);
  }

}
