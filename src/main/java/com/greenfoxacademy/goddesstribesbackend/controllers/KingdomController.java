package com.greenfoxacademy.goddesstribesbackend.controllers;

import com.greenfoxacademy.goddesstribesbackend.models.MockData;
import com.greenfoxacademy.goddesstribesbackend.models.dtos.ErrorMessage;
import com.greenfoxacademy.goddesstribesbackend.models.dtos.KingdomDTO;
import com.greenfoxacademy.goddesstribesbackend.models.dtos.KingdomNameDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KingdomController {

  @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",required = true,  dataType = "string", paramType = "header")})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = KingdomDTO.class)})
  @GetMapping("/kingdom")
  public ResponseEntity<Object> findOwnKingdom() {

    return ResponseEntity.status(200).body(MockData.kingdomDTO);
  }

  @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = KingdomDTO.class), @ApiResponse(code = 400, message = "Missing parameter(s): name!", response = ErrorMessage.class)})
  @PutMapping("/kingdom")
  public ResponseEntity<Object> mockChangeKingdomName(@RequestBody KingdomNameDTO kingdomNameDTO) {

    if (kingdomNameDTO.getName() == null || kingdomNameDTO.getName() == "") {
      return ResponseEntity.status(400).body(new ErrorMessage("Missing parameter(s): <name>!"));
    }

    MockData.kingdomDTO.setKingdomName(kingdomNameDTO.getName());
    return ResponseEntity.status(200).body(MockData.kingdomDTO);
  }

  @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = KingdomDTO.class), @ApiResponse(code = 404, message = "Id not found", response = ErrorMessage.class)})
  @GetMapping("/kingdom/{id}")
  public ResponseEntity<Object> mockRenderKingdom(@PathVariable Long id) {

    if (MockData.kingdomDTO.getId() == id) {
      return ResponseEntity.status(200).body(MockData.kingdomDTO);
    }

    return ResponseEntity.status(404).body(new ErrorMessage("Id not found"));
  }

}
