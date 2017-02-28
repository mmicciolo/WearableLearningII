package wlxi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wlxi.GameStateInfo;
import wlxi.GameInfo;

public class XMLParser {
	private File file = null;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	
	public XMLParser() {
		
	}
	
	public XMLParser(File file) {
		this.file = file;
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			
		}
	}
	
	public GameInfo parseGameInfo() {
		NodeList list = getNodeList("game");
		Node node = getNodeFromNodeList(list, 0);
		String title = getNodeElement(node, "title");
		int teamCount = Integer.parseInt(getNodeElement(node, "teamCount"));
		int playersPerTeam = Integer.parseInt(getNodeElement(node, "playersPerTeam"));
		return new GameInfo(-1, title, teamCount, playersPerTeam);
	}
	
	public List<GameStateInfo> parseGameStateInfo() {
		List<GameStateInfo> list = new ArrayList<GameStateInfo>();
		NodeList nodeList = getNodeList("state");
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = getNodeFromNodeList(nodeList, i);
			List<Integer> buttons = new ArrayList<Integer>();
			if((Integer.parseInt(getNodeElement(node, "teamId")) == 0) && (Integer.parseInt(getNodeElement(node, "playerId")) == 0)) {
//				buttons.add(Integer.parseInt(getNodeElement(node, "button1")));
//				buttons.add(Integer.parseInt(getNodeElement(node, "button2")));
//				buttons.add(Integer.parseInt(getNodeElement(node, "button3")));
//				buttons.add(Integer.parseInt(getNodeElement(node, "button4")));
				for(int n = 1; n <  13; n++) {
					buttons.add(Integer.parseInt(getNodeElement(node, "button" + n)));
				}
			} else if((Integer.parseInt(getNodeElement(node, "teamId")) > 0) && (Integer.parseInt(getNodeElement(node, "playerId")) == 0)) {
				for(int n = 1; n <  13; n++) {
					buttons.add(Integer.parseInt(getNodeElement(node, "button" + n)));
				}
				
			} else if((Integer.parseInt(getNodeElement(node, "teamId")) > 0) && (Integer.parseInt(getNodeElement(node, "playerId")) == -1)) {
				for(int n = 1; n <  13; n++) {
					buttons.add(Integer.parseInt(getNodeElement(node, "button" + n)));
				}
				
			}else if((Integer.parseInt(getNodeElement(node, "teamId")) == 0) && (Integer.parseInt(getNodeElement(node, "playerId")) > 0)) {
				buttons.add(Integer.parseInt(getNodeElement(node, "button1")));
				buttons.add(Integer.parseInt(getNodeElement(node, "button2")));
				buttons.add(Integer.parseInt(getNodeElement(node, "button3")));
				buttons.add(Integer.parseInt(getNodeElement(node, "button4")));
			}
			list.add(new GameStateInfo(-1, Integer.parseInt(getNodeElement(node, "gameStateCount")),Integer.parseInt(getNodeElement(node, "teamId")),Integer.parseInt(getNodeElement(node, "playerId")), getNodeElement(node, "text"), getNodeElement(node, "buttonInputType"), buttons));
		}
		return list;
	}
	
	public NodeList getNodeList(String attribute) {
		return doc.getElementsByTagName(attribute);
	}
	
	public Node getNodeFromNodeList(NodeList nodeList, int index) {
		return nodeList.item(index);
	}
	
	public String getNodeElement(Node node, String attribute) {
		Element element = (Element) node;
		return element.getElementsByTagName(attribute).item(0).getTextContent();
	}
}
