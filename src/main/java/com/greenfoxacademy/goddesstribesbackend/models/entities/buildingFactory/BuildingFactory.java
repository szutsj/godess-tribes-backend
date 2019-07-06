package com.greenfoxacademy.goddesstribesbackend.models.entities.buildingFactory;

import com.greenfoxacademy.goddesstribesbackend.models.BuildingTypeENUM;
import com.greenfoxacademy.goddesstribesbackend.models.entities.*;
import org.springframework.stereotype.Component;

@Component
public class BuildingFactory {

  public Building getBuilding(String type, Kingdom kingdom){

    if (type.equalsIgnoreCase(BuildingTypeENUM.FARM.toString())) {
      return new Farm(kingdom);
    } else if (type.equalsIgnoreCase(BuildingTypeENUM.MINE.toString())) {
      return new Mine(kingdom);
    } else if (type.equalsIgnoreCase(BuildingTypeENUM.BARRACK.toString())) {
      return new Barrack(kingdom);
    }
    return null;
  }

}
