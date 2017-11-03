package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dkeep.logic.Door;
import dkeep.logic.Dragon;
import dkeep.logic.Element;
import dkeep.logic.GameState;
import dkeep.logic.Hero;
import dkeep.logic.Map;
import dkeep.logic.Sword;

public class Junit {
	public static Map mapa;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mapa = new Map();
		Hero hero = new Hero(new int[] {1,1},mapa,GameState.heroI);
		Door door =  new Door(new int[] {5,9},mapa,GameState.closedD);
		mapa.setChar(hero);
		mapa.setHero(hero);
		mapa.setChar(door);
		mapa.setDoor(door);
		System.out.println("New map and hero created");
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Finishing tests");
	}
	@Before
	public void setUp() throws Exception {					//Resets Map -> Hero to {1,1}
		System.out.println("Reseting map");
		mapa.testMove(mapa.getHero(),new int[] {1,1});
		mapa.getHero().setArmed(false);
		mapa.getHero().setAlive(true);
		if(mapa.testGame()) mapa.changeGame();
		if(mapa.testWin()) mapa.changeWin();
		mapa.getHero().setHasKey(false);
		mapa.setChar(new Element(new int[] {5,9},'E',mapa,GameState.closedD));
		mapa.printMap();
	}
	@After
	public void tearDown() throws Exception {
		mapa.printMap();
	}
	@Test
	public void testFreeMovement() {
		System.out.println("Testing down");
		int[] down = new int[] {2,1};
		mapa.testMove(mapa.getHero(),down);
		assertEquals("Down was free cell",2,mapa.getHero().getCoord()[0]);
		System.out.print("Successfuly moved down\n");
		
	}
	@Test
	public void testWallMovement() {
		System.out.println("Testing up wall");
		int[] up = new int[] {0,1};
		mapa.testMove(mapa.getHero(),up);
		assertNotEquals("Up wasn't free cell",0,mapa.getHero().getCoord()[0]);
		System.out.print("Successfuly didn't move up\n");
	}
	@Test
	public void testArming() {
		System.out.println("Testing arming");
		Sword sword = new Sword(new int[] {1,2},mapa,GameState.swordI);
		mapa.setChar(sword);
		mapa.setSword(sword);
		mapa.printMap();
		mapa.testMove(mapa.getHero(),new int[] {1,2});
		mapa.printMap();
		assertEquals("Sword not picked up",true,mapa.getHero().getArmed());
		System.out.print("Successfuly armed\n");
		mapa.setChar(new Element(new int[] {1,2},' ',mapa,GameState.space));
	}
	@Test
	public void testUnarmedDragon() {
		System.out.println("Testing dragon Unarmed");
		Dragon dragon = new Dragon(new int[] {1,3},mapa,GameState.dragonI);
		mapa.setChar(dragon);									//Inserting new Dragon
		ArrayList<Dragon> dragons =  new ArrayList<Dragon>(1);
		dragons.add(dragon);
		mapa.setList(dragons);
		mapa.printMap();
		mapa.testMove(mapa.getHero(),new int[] {1,2});
		mapa.validDragons();
		mapa.printMap();
		assertEquals("Hero alive and adjacent to a Dragon",false,mapa.getHero().getAlive());
		assertEquals("Game isn't over",true,mapa.testGame());
		System.out.print("Successfuly died, and game lost\n");
		mapa.setChar(new Element(new int[] {1,3},' ',mapa,GameState.space));	
		mapa.getList().clear();
	}
	@Test
	public void testArmedDragon() {
		System.out.println("Testing dragon Armed");
		mapa.getHero().setArmed(true);							//Arming Hero
		Dragon dragon = new Dragon(new int[] {1,3},mapa,GameState.dragonI);	
		mapa.setChar(dragon);
		ArrayList<Dragon> dragons =  new ArrayList<Dragon>(1);
		dragons.add(dragon);
		mapa.setList(dragons);
		mapa.printMap();
		mapa.testMove(mapa.getHero(),new int[] {1,2});
		mapa.validDragons();
		mapa.printMap();
		assertEquals("Hero dead and armed",true,mapa.getHero().getAlive());
		System.out.print("Successfuly killed dragon\n");
		mapa.setChar(new Element(new int[] {1,3},' ',mapa,GameState.space));	
		mapa.getList().clear();
		
	}
	@Test
	public void testExitArmedDragonKilled() {
		System.out.println("Testing exit dragon killed");
		mapa.getHero().setArmed(true);							//Arming Hero
		mapa.testMove(mapa.getHero(),new int[] {5,8});			//Moving to exit
		Dragon dragon = new Dragon(new int[] {4,8},mapa,GameState.dragonI);		//Spawning dragon next to hero
		mapa.setChar(dragon);
		ArrayList<Dragon> dragons =  new ArrayList<Dragon>(1);
		dragons.add(dragon);
		mapa.setList(dragons);
		mapa.printMap();
		mapa.validDragons();									//Slaying dragon -> gets Key
		mapa.printMap();
		mapa.testMove(mapa.getHero(),new int[] {5,9});
		mapa.printMap();
		assertEquals("Game not won",true,mapa.testWin());
		assertEquals("Hero not in exit row",5,mapa.getHero().getCoord()[0]);
		assertEquals("Hero not in exit col",9,mapa.getHero().getCoord()[1]);
		System.out.print("Successfuly won game\n");
		mapa.setChar(new Element(new int[] {4,8},' ',mapa,GameState.space));
		mapa.getList().clear();
		
	}
	@Test
	public void testExitUnarmed() {
		System.out.println("Testing exit unarmed");
		mapa.testMove(mapa.getHero(),new int[] {5,8});			//Moving to exit
		System.out.println("Moving to exit");
		mapa.printMap();
		mapa.testMove(mapa.getHero(),new int[] {5,9});
		assertNotEquals("Hero in exit col",9,mapa.getHero().getCoord()[1]);
		System.out.print("Successfuly didnt exit\n");
	}
	@Test
	public void testExitArmedNotSlain() {
		System.out.println("Testing exit armed dragon not slain");
		mapa.testMove(mapa.getHero(),new int[] {5,8});			//Moving to exit
		mapa.getHero().setArmed(true);
		mapa.printMap();
		Dragon dragon = new Dragon(new int[] {1,3},mapa,GameState.dragonI);	
		mapa.setChar(dragon);
		ArrayList<Dragon> dragons =  new ArrayList<Dragon>(1);
		dragons.add(dragon);
		mapa.setList(dragons);
		mapa.printMap();
		mapa.testMove(mapa.getHero(),new int[] {5,9});
		assertNotEquals("Hero in exit col",9,mapa.getHero().getCoord()[1]);
		System.out.print("Successfuly didnt exit\n");
		mapa.setChar(new Element(new int[] {1,3},' ',mapa,GameState.space));
		mapa.getList().clear();
	}
	@Test
	public void testSleepyDragonUnarmed() {
		System.out.println("Testing sleepy dragon Unarmed");
		Dragon dragon = new Dragon(new int[] {1,3},mapa,GameState.dragonI);
		dragon.changeSleep();									//Put dragon to sleep
		mapa.setChar(dragon);									//Inserting new Dragon
		ArrayList<Dragon> dragons =  new ArrayList<Dragon>(1);
		dragons.add(dragon);
		mapa.setList(dragons);
		mapa.printMap();
		mapa.testMove(mapa.getHero(),new int[] {1,2});
		mapa.validDragons();
		mapa.printMap();
		assertEquals("Hero dead and adjacent to a sleepy Dragon",true,mapa.getHero().getAlive());
		System.out.print("Successfuly lived\n");
		mapa.setChar(new Element(new int[] {1,3},' ',mapa,GameState.space));	
		mapa.getList().clear();
	}
	@Test
	public void testSleepyDragonArmed() {
		System.out.println("Testing sleepy dragon Armed");
		mapa.getHero().setArmed(true);							//Arming hero
		Dragon dragon = new Dragon(new int[] {1,3},mapa,GameState.dragonI);
		dragon.changeSleep();									//Put dragon to sleep
		mapa.setChar(dragon);									//Inserting new Dragon
		ArrayList<Dragon> dragons =  new ArrayList<Dragon>(1);
		dragons.add(dragon);
		mapa.setList(dragons);
		mapa.printMap();
		mapa.testMove(mapa.getHero(),new int[] {1,2});
		mapa.validDragons();
		mapa.printMap();
		assertEquals("Hero dead",true,mapa.getHero().getAlive());
		System.out.print("Successfuly slain\n");
		mapa.setChar(new Element(new int[] {1,3},' ',mapa,GameState.space));	
		mapa.getList().clear();
	}
}