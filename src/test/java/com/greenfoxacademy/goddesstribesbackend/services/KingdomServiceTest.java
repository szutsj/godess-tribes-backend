package com.greenfoxacademy.goddesstribesbackend.services;

import com.greenfoxacademy.goddesstribesbackend.models.entities.Kingdom;
import com.greenfoxacademy.goddesstribesbackend.models.entities.User;
import com.greenfoxacademy.goddesstribesbackend.repositories.KingdomRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class KingdomServiceTest {

  private static KingdomService kingdomService;

  private static KingdomRepository kingdomRepositoryMock = Mockito.mock(KingdomRepository.class);
  private static UserService userServiceMock = Mockito.mock(UserService.class);
  private static BuildingService buildingServiceMock = Mockito.mock(BuildingService.class);
  private static ResourceService resourceServiceMock = Mockito.mock(ResourceService.class);
  private static SoldierService soldierServiceMock = Mockito.mock(SoldierService.class);
  private static ProductionService productionServiceMock = Mockito.mock(ProductionService.class);

  @BeforeClass
  public static void init() {
    kingdomService = new KingdomService(kingdomRepositoryMock, userServiceMock,
                                        buildingServiceMock, resourceServiceMock,
                                        soldierServiceMock, productionServiceMock);
  }

  @Test
  public void saveKingdomShouldReturnProperResult_when_KingdomIsSaved() {
    String username = "Juliska";
    String password = "jancsi123";
    String kingdomName = "Tündérország";
    User user = new User(username, password);
    Kingdom expectedKingdom = new Kingdom(kingdomName, user);

    when(userServiceMock.checkUserByName(any())).thenReturn(true);
    when(kingdomRepositoryMock.save(any())).thenReturn(expectedKingdom);

    Kingdom resultKingdom = kingdomService.saveKingdom(kingdomName, user);
    assertEquals(expectedKingdom, resultKingdom);
  }

  @Test
  public void saveKingdomShouldReturnNull_when_UsernameIsTaken() {
    String username = "Juliska";
    String password = "jancsi123";
    String kingdomName = "Tündérország";
    User user = new User(username, password);
    Kingdom expectedKingdom = null;

    when(userServiceMock.checkUserByName(any())).thenReturn(false);

    Kingdom resultKingdom = kingdomService.saveKingdom(kingdomName, user);
    assertEquals(expectedKingdom, resultKingdom);
    assertNull(resultKingdom);
  }

  @Test
  public void saveKingdomShouldReturnNull_when_UserIsNull() {
    String kingdomName = "Tündérország";
    User user = null;
    Kingdom expectedKingdom = null;

    Kingdom resultKingdom = kingdomService.saveKingdom(kingdomName, user);
    assertEquals(expectedKingdom, resultKingdom);
    assertNull(resultKingdom);
  }

  @Test
  public void saveKingdomShouldReturnProperResult_when_KingdomNameIsNull() {
    String username = "Juliska";
    String password = "jancsi123";
    String kingdomName = null;
    User user = new User(username, password);
    String expectedKingdomName = username + "'s kingdom";

    when(userServiceMock.checkUserByName(any())).thenReturn(true);
    when(kingdomRepositoryMock.save(any())).then(returnsFirstArg());

    Kingdom resultKingdom = kingdomService.saveKingdom(kingdomName, user);
    assertEquals(expectedKingdomName, resultKingdom.getKingdomName());
  }

  @Test
  public void renameKingdomShouldReturnProperResult_when_KingdomIsRenamed() {
    String username = "Juliska";
    String password = "jancsi123";
    String kingdomName = "Tündérország";
    User user = new User(username, password);
    Kingdom kingdom = new Kingdom(kingdomName, user);
    String newName = "Sárkányország";

    when(kingdomRepositoryMock.save(any())).then(returnsFirstArg());

    Kingdom renamedKingdom = kingdomService.renameKingdom(newName, kingdom);
    assertEquals(newName, renamedKingdom.getKingdomName());
  }

}
