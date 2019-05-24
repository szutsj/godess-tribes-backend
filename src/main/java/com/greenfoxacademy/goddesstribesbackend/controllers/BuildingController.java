package com.greenfoxacademy.goddesstribesbackend.controllers;

import com.greenfoxacademy.goddesstribesbackend.models.MockData;
import com.greenfoxacademy.goddesstribesbackend.models.dtos.BuildingDTO;
import com.greenfoxacademy.goddesstribesbackend.models.dtos.BuildingTypeDTO;
import com.greenfoxacademy.goddesstribesbackend.models.dtos.ErrorMessage;
import com.greenfoxacademy.goddesstribesbackend.models.dtos.LevelDTO;
import com.greenfoxacademy.goddesstribesbackend.models.entities.Building;
import com.greenfoxacademy.goddesstribesbackend.models.entities.Kingdom;
import com.greenfoxacademy.goddesstribesbackend.services.BuildingService;
import com.greenfoxacademy.goddesstribesbackend.services.KingdomService;
import com.greenfoxacademy.goddesstribesbackend.services.ProductionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class BuildingController {

  private KingdomService kingdomService;
  private ProductionService productionService;
  private BuildingService buildingService;

  @Autowired
  public BuildingController(KingdomService kingdomService, ProductionService productionService,
                            BuildingService buildingService) {
    this.kingdomService = kingdomService;
    this.productionService = productionService;
    this.buildingService = buildingService;
  }

  @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = BuildingDTO.class)})
  @GetMapping("/kingdom/buildings")
  public ResponseEntity<Object> mockListOfBuildings() {
    return ResponseEntity.status(200).body(MockData.buildingsDTO);
  }

  @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = BuildingDTO.class), @ApiResponse(code = 400, message = "Missing parameter(s): type!", response = ErrorMessage.class), @ApiResponse(code = 406, message = "Invalid building type", response = ErrorMessage.class), @ApiResponse(code = 409, message = "Not enough resource", response = ErrorMessage.class)})
  @PostMapping("/kingdom/buildings")
  public ResponseEntity<Object> createBuilding(@RequestBody BuildingTypeDTO buildingTypeDTO) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Kingdom kingdom = kingdomService.findKingdomByUsername(username);
    String type = buildingTypeDTO.getType();

    if (type == null || type.isEmpty()) {
      return ResponseEntity.status(400).body(new ErrorMessage("Missing parameter(s): type!"));
    }

    if (!buildingService.isValidBuildingType(type)) {
      return ResponseEntity.status(406).body(new ErrorMessage("Invalid building type"));
    }

    if (!productionService.isEnoughMoneyToCreateBuilding(kingdom.getId())) {
      return ResponseEntity.status(409).body(new ErrorMessage("Not enough resource"));
    }

    Building building = buildingService.createBuilding(kingdom, type);
    return ResponseEntity.status(200).body(buildingService.createBuildingDTO(building));
  }

  @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = BuildingDTO.class), @ApiResponse(code = 409, message = "Id not found", response = ErrorMessage.class)})
  @GetMapping("/kingdom/buildings/{id}")
  public ResponseEntity<Object> mockRenderBuilding(@PathVariable Long id) {

    if (MockData.farm.getId() == id) {
      return ResponseEntity.status(200).body(MockData.farm);
    }

    return ResponseEntity.status(404).body(new ErrorMessage("Id not found"));
  }

  @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = BuildingDTO.class), @ApiResponse(code = 400, message = "Missing parameter(s): level!", response = ErrorMessage.class), @ApiResponse(code = 404, message = "Id not found", response = ErrorMessage.class), @ApiResponse(code = 406, message = "Invalid building level", response = ErrorMessage.class), @ApiResponse(code = 409, message = "Not enough resource", response = ErrorMessage.class)})
  @PutMapping("/kingdom/buildings/{id}")
  public ResponseEntity<Object> mockChangeBuildingLevel(
          @PathVariable Long id,
          @RequestBody LevelDTO levelDTO) {

    if (levelDTO.getLevel() == null) {
      return ResponseEntity.status(400).body(new ErrorMessage("Missing parameter(s): <level>!"));
    }

    if (MockData.farm.getId() != id) {
      return ResponseEntity.status(404).body(new ErrorMessage("Id not found!"));
    }

    if (levelDTO.getLevel() > 3 || levelDTO.getLevel() < 1) {
      return ResponseEntity.status(406).body(new ErrorMessage("Invalid building level!"));
    }

    if (MockData.gold.getAmount() < MockData.COST_OF_NEW_BUILDING) {
      return ResponseEntity.status(409).body(new ErrorMessage("Not enough resource!"));
    }

    MockData.farm.setLevel(levelDTO.getLevel());
    return ResponseEntity.status(200).body(MockData.farm);
  }

}
