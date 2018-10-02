import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class GraphProcessorTest {
	GraphProcessor graph;
	String expected;
	String actual;

	@Before
	public void setUp() throws Exception {
		List<String> dict = new ArrayList<String>();
		dict.add("cat");
		dict.add("rat");
		dict.add("hat");
		dict.add("neat");
		dict.add("wheat");
		dict.add("kit");
		this.graph = new GraphProcessor();
	}
	@After
	public void tearDown() throws Exception {
		this.graph = null;
	}
	@Test
	void test0_populate_with_invalid_filepath() {
		assertEquals("-1",graph.populate("A") + "");
	}
	@Test
	void test1_populate_valid_filepath() {
		assertEquals("7", graph.populate("src/testWordList.txt"));
	}
	@Test
	void test2_invalid_filepath_log() {
		try {
			graph.populate("A");
			fail("Graph.populate() Did not throw IOException when invalid filepath passed");
		} catch(Exception e) {}
		
	}
	@Test
	void test3_getShortestPath_multiple_vertices() {
		graph.populate("testWordList.txt");
		ArrayList<String> path = new ArrayList<String>((ArrayList<String>) graph.getShortestPath("cat", "wheat"));
		ArrayList<String> expectedPath = new ArrayList<String>();
		expectedPath.add("cat");
		expectedPath.add("hat");
		expectedPath.add("heat");
		expectedPath.add("neat");
		for(int i = 0; i < path.size(); i++)
		{
			assertEquals(expectedPath.get(i), path.get(i));
		}
	}
	@Test
	void test4_getShortestPath_invalid_path() {
		graph.populate("src/testWordList.txt");
		ArrayList<String> path = new ArrayList<String>((ArrayList<String>) graph.getShortestPath("cat", "kit"));
		assertEquals(null, path);
	}
	@Test
	void test5_getShortestPath_no_vertices() {
		GraphProcessor graph1 = new GraphProcessor();
		ArrayList<String> path = new ArrayList<String>((ArrayList<String>) graph1.getShortestPath("cat", "wheat"));
		assertEquals(null, path);
	}
	@Test
	void test_6_getShortestPath_same_vertices() {
		graph.populate("src/testWordList.txt");
		ArrayList<String> path = new ArrayList<String>((ArrayList<String>) graph.getShortestPath("cat", "cat"));
		assertEquals(0, path.size());
	}
	@Test
	void test_getShortestDistance_multiple_vertices() {
		graph.populate("src/testWordList.txt");
		assertEquals("3",graph.getShortestDistance("cat", "wheat") + "");
	}
	@Test
	void test_getShortestDistance_same_vertices() {
		graph.populate("src/testWordList.txt");
		assertEquals("-1", graph.getShortestDistance("cat", "cat") + "");
	}
	@Test
	void test_getShortestDistance_no_path() {
		graph.populate("src/testWordList.txt");
		assertEquals("-1", graph.getShortestDistance("cat", "bat"));
	}
	@Test
	void test_getWordStream_valid_filepath() {
		
	}
	@Test
	public final void test_wordProcessorStream() {
		try {
			List<String> words = Files.lines(Paths.get("src/word_list")).map(w -> w.trim().toUpperCase()).filter(w -> w != "").collect(Collectors.toList());
			for(int i = 0; i < words.size(); i++) {
				if(!words.get(i).equals(words.get(i).toUpperCase())) {
					fail("Stream did not return all uppercase words");
				}
				if(words.get(i) == "") {
					fail("Stream returned an empty line");
				}
				for(int j = 0; j < words.get(i).length(); j++) {
					if(words.get(i).charAt(j) == ' ') {
						fail("Stream did not trim all lines");
					}
				}
			}
		} catch (IOException e) {
			fail("Could not get file at specified path");
		}	
	}

}
