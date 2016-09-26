import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;


public class PersonTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void person_instantiatesCorrectly_true() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    assertEquals(true, testPerson instanceof Person);
  }

  @Test
  public void getName_personInstantiatesWithName_Henry() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    assertEquals("Henry", testPerson.getName());
  }

  @Test
  public void getEmail_personInstantiatesWithEmail_Henry() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    assertEquals("henry@henry.com", testPerson.getEmail());
  }

  @Test
  public void equals_returnsTrueIfNameAndEmailAreSame_true() {
    Person firstPerson = new Person("Henry", "henry@henry.com");
    Person anotherPerson = new Person("Henry", "henry@henry.com");
    assertTrue(firstPerson.equals(anotherPerson));
  }
  //in person.java this is the test for @Override function


  @Test
  public void save_insertsObjectIntoDatabase_Person() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    assertTrue(Person.all().get(0).equals(testPerson));
  }
  //save function


  @Test
  public void all_returnsAllInstancesOfPerson_True() {
    Person firstPerson = new Person("Henry", "henry@henry.com");
    firstPerson.save();
    Person anotherPerson = new Person("Henry", "henry@henry.com");
    anotherPerson.save();
    assertEquals(true, Person.all().get(0).equals(firstPerson));
    assertEquals(true, Person.all().get(1).equals(anotherPerson));
    //all function of selecting all info from person from the database
  }

  @Test
  public void save_assignsIdToObject() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    Person savedPerson = Person.all().get(0);
    assertEquals(testPerson.getId(), savedPerson.getId());
  }

  @Test
  public void find_returnsPersonWithSameId_secondPerson() {
    Person firstPerson = new Person("Henry", "henry@henry.com");
    firstPerson.save();
    Person secondPerson = new Person("Harriet", "harriet@harriet.com");
    secondPerson.save();
    assertEquals(Person.find(secondPerson.getId()), secondPerson);
  }

  @Test
  public void getMonsters_retrievesAllMonstersFromDatabase_monstersList() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    Monster firstMonster = new Monster("Bubbles", testPerson.getId());
    firstMonster.save();
    Monster secondMonster = new Monster("Spud", testPerson.getId());
    secondMonster.save();
    Monster[] monsters = new Monster[] { firstMonster, secondMonster };
    assertTrue(testPerson.getMonsters().containsAll(Arrays.asList(monsters)));
  }


}
