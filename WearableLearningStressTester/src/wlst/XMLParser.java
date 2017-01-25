package wlst;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	
	public ServerInfo parseServerInfo() {
		NodeList list = getNodeList("ServerInfo");
		Node node = getNodeFromNodeList(list, 0);
		String serverIp = getNodeElement(node, "ServerIp");
		int portNumber = Integer.parseInt(getNodeElement(node, "PortNumber"));
		return new ServerInfo(serverIp, portNumber);
	}
	
	public GameInfo parseGameInfo() {
		NodeList list = getNodeList("GameInfo");
		Node node = getNodeFromNodeList(list, 0);
		int gameId = Integer.parseInt(getNodeElement(node, "GameId"));
		String title = getNodeElement(node, "Title");
		int teamCount = Integer.parseInt(getNodeElement(node, "TeamCount"));
		int playersPerTeam = Integer.parseInt(getNodeElement(node, "PlayersPerTeam"));
		return new GameInfo(gameId, title, teamCount, playersPerTeam);
	}
	
	public List<PlayerInfo> parsePlayerInfo() {
		List<PlayerInfo> list = new ArrayList<PlayerInfo>();
		NodeList nodeList = getNodeList("Player");
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = getNodeFromNodeList(nodeList, i);
			list.add(new PlayerInfo(getNodeElement(node, "StudentName"), Integer.parseInt(getNodeElement(node, "GameInstanceId")), Integer.parseInt(getNodeElement(node, "TeamNumber")), Integer.parseInt(getNodeElement(node, "TeamPlayerNumber"))));
			
		}
		return list;	
	}
	
	public List<GameStateInfo> parseGameStateInfo() {
		List<GameStateInfo> list = new ArrayList<GameStateInfo>();
		NodeList nodeList = getNodeList("State");
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = getNodeFromNodeList(nodeList, i);
			List<Integer> buttons = new ArrayList<Integer>();
			if(Integer.parseInt(getNodeElement(node, "TeamNumber")) == 0 && Integer.parseInt(getNodeElement(node, "TeamPlayerNumber")) == 0) {
				buttons.add(Integer.parseInt(getNodeElement(node, "Button1")));
				buttons.add(Integer.parseInt(getNodeElement(node, "Button2")));
				buttons.add(Integer.parseInt(getNodeElement(node, "Button3")));
				buttons.add(Integer.parseInt(getNodeElement(node, "Button4")));
			}
			else if(Integer.parseInt(getNodeElement(node, "TeamNumber")) > 0 && Integer.parseInt(getNodeElement(node, "TeamPlayerNumber")) == 0) {
				NodeList l = node.getChildNodes();
				for(int n = 0; n < l.getLength(); n++) {
					Node no = getNodeFromNodeList(l, n);
					if(no.getNodeName().equals("Team")){
						buttons.add(Integer.parseInt(getNodeElement(node, "Button1")));
						buttons.add(Integer.parseInt(getNodeElement(node, "Button2")));
						buttons.add(Integer.parseInt(getNodeElement(node, "Button3")));
						buttons.add(Integer.parseInt(getNodeElement(node, "Button4")));
					}
				}
			}
			else if(Integer.parseInt(getNodeElement(node, "TeamNumber")) == 0 && Integer.parseInt(getNodeElement(node, "TeamPlayerNumber")) > 0) {
				buttons.add(Integer.parseInt(getNodeElement(node, "Button1")));
				buttons.add(Integer.parseInt(getNodeElement(node, "Button2")));
				buttons.add(Integer.parseInt(getNodeElement(node, "Button3")));
				buttons.add(Integer.parseInt(getNodeElement(node, "Button4")));
			}
			list.add(new GameStateInfo(Integer.parseInt(getNodeElement(node, "GameStateId")),Integer.parseInt(getNodeElement(node, "GameStateCount")),Integer.parseInt(getNodeElement(node, "TeamNumber")),Integer.parseInt(getNodeElement(node, "TeamPlayerNumber")), getNodeElement(node, "TextContent"), buttons));
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
