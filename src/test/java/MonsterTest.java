import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class MonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void monster_instantiatesCorrectly_true() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(true, testMonster instanceof Monster);
  }

  @Test
  public void Monster_instantiatesWithPersonId_int() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(1, testMonster.getPersonId());
  }

  @Test
  public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
    Monster testMonster = new Monster("Bubbles", 1);
    Monster anotherMonster = new Monster("Bubbles", 1);
    assertTrue(testMonster.equals(anotherMonster));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    assertTrue(Monster.all().get(0).equals(testMonster));
  }

  @Test
  public void save_assignsIdToMonster() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    Monster savedMonster = Monster.all().get(0);
    assertEquals(savedMonster.getId(), testMonster.getId());
  }

  @Test
  public void all_returnsAllInstancesOfMonster_true() {
    Monster firstMonster = new Monster("Bubbles", 1);
    firstMonster.save();
    Monster secondMonster = new Monster("Spud", 1);
    secondMonster.save();
    assertEquals(true, Monster.all().get(0).equals(firstMonster));
    assertEquals(true, Monster.all().get(1).equals(secondMonster));
  }

  @Test
  public void find_returnsMonsterWithSameId_secondMonster() {
    Monster firstMonster = new Monster("Bubbles", 1);
    firstMonster.save();
    Monster secondMonster = new Monster("Spud", 3);
    secondMonster.save();
    assertEquals(Monster.find(secondMonster.getId()), secondMonster);
  }

  @Test
  public void save_savesPersonIdIntoDB_true() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    Monster testMonster = new Monster("Bubbles", testPerson.getId());
    testMonster.save();
    Monster savedMonster = Monster.find(testMonster.getId());
    assertEquals(savedMonster.getPersonId(), testPerson.getId());
  }

  @Test
  public void monster_instantiatesWithHalfFullPlayLevel(){
  Monster testMonster = new Monster("Bubbles", 1);
  assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2 ));
  // instead of using a number value here, we simply refer to the MAX_PLAY_LEVEL constant. That way, if we later want to alter these constants, we won't need to manually update each
  }

  @Test
  public void monster_instantiatesWithHalfFullSleepLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void monster_instantiatesWithHalfFullFoodLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void isAlive_confirmsMonsterIsAliveIfAllLevelsAboveMinimum_true(){
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.isAlive(), true);
  }
  //ASSERTRUE can be used??

  @Test
  public void depleteLevels_reducesAllLevels(){
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.depleteLevels(), (Monster.MAX_FOOD_LEVEL / 2) -1);
    assertEquals(testMonster.depleteLevels(), (Monster.MAX_SLEEP_LEVEL / 2) -1);
    assertEquals(testMonster.depleteLevels(), (Monster.MAX_PLAY_LEVEL / 2) -1);
  }

  @Test
  public void isAlive_recognizesMonsterIsDeadWhenLevelsReachMinimum_false(){
    Monster testMonster = new Monster("Bubbles", 1);
    for (int i = Monster.MIN_ALL_LEVELS; i <= MAX_PLAY_LEVEL; i++) {
      testMonster.depleteLevels();
    }
    assertEquals(testMonster.isAlive(), true);
  }
// use our constants in a loop. This ensures we call depleteLevels() the necessary number of times to make at least one level reach the minimum

  public void play_increasesMonsterPlayLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.play();
    assertTrue(testMonster.play() > (Monster.MAX_PLAY_LEVEL / 2));
  }

  @Test
  public void sleep_increasesMonsterSleepLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.sleep();
    assertTrue(testMonster.getSleepLevel() > (Monster.MAX_SLEEP_LEVEL / 2));
  }

  @Test
  public void feed_increasesMonsterFoodLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.feed();
    assertTrue(testMonster.getFoodLevel() > (Monster.MAX_FOOD_LEVEL / 2));
  }

  @Test
  public void monster_foodLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for (int i = Monster.MIN_ALL_LEVELS;i <= (MAX_FOOD_LEVEL + 2) ; i++ ) {
      try {
        testMonster.feed();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testMonster.getFoodLevel() <= Monster.MAX_FOOD_LEVEL)
  }
  // We don't want our pet's foodLevel to surpass 3


  @Test(expected = UnsupportedOperationException.class)
  //tells JUnit that we expect an UnsupportedOperationException from this test
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue() {
    Monster testMonster = new Monster("Bubbles", 1);
    for (int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++ ) {
      testMonster.feed();
    }
  }
  //JUnit fails a test if it receive an exception of any kind
  // we're specifically testing for an exception, we must let JUnit know that we're intentionally looking for that exception

  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue() {
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++){
      testMonster.play();
    }
  }

  @Test
  public void monster_playLevelCannotGoBeyondMaxValue() {
    Monster testMonster = new Monster("Bubbles", 1);
    for (int i = Monster.MIN_ALL_LEVELS;i <= MAX_PLAY_LEVEL; i++ ) {
      try {
        testMonster.play();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testMonster.getPlayLevel() <= Monster.MAX_PLAY_LEVEL)
  }

  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfSleepLevelIsAtMaxValue() {
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
      testMonster.sleep();
    }
  }

  @Test
  public void monster_sleepLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for (int i = Monster.MIN_ALL_LEVELS; i <= Monster.MAX_SLEEP_LEVEL; i++ ) {
      try {
        testMonster.sleep();
      } catch (UnsupportedOperationException exception) { }
      assertTrue(testMonster.getSleepLevel() <= Monster.MAX_SLEEP_LEVEL);
    }

}
