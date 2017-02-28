package wlxi;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import wlxi.MySQLAccessor;
import wlxi.XMLParser;

public class Main {
	
	private static String fileName = "";
	private static File file = null;
	private static XMLParser parser;
	private static GameInfo gameInfo;
	private static List<GameStateInfo> gameStateInfo;
	private static MySQLAccessor accessor;

	public static void main(String[] args) {
		
		//Check the args
		if(args.length < 1) {
			System.out.println("Wrong arguments, enter only filename");
			return;
		}
		
		//Set the filename
		fileName = args[0];
		
		//Open the file
		file = new File(fileName);
		
		//Make sure the file isn't null
		if(file == null) {
			System.out.println("Error opening file " + fileName);
			return;
		}
		
		//Create a new parser
		parser = new XMLParser(file);
		
		//Make sure everything went ok
		if(parser == null) {
			System.out.println("Could not create XML parser");
			return;
		}
		
		//Parse game and game state info
		gameInfo = parser.parseGameInfo();
		gameStateInfo = parser.parseGameStateInfo();
		
		//Try and get an instance of the mysql accessor
		accessor = MySQLAccessor.getInstance();
		if(!accessor.Connect()) {
			System.out.println("Could not connect to database");
			return;
		}
		
		//Dump everything to the database
		try {
			String returnId[] = {"gameId"};
			PreparedStatement preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO games (title, teamCount, playersPerTeam) VALUES (?, ?, ?)", returnId);
			preparedStatement.setString(1, gameInfo.getTitle());
			preparedStatement.setInt(2, gameInfo.getTeamCount());
			preparedStatement.setInt(3, gameInfo.getPlayersPerTeam());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) { gameInfo.setGameId((int)resultSet.getLong(1)); }
			resultSet.close();
			preparedStatement.close();
			
			for(GameStateInfo info : gameStateInfo) {
				String returnId2[] = {"gameStateId"};
				preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO gameState (gameId, teamId, playerId, hintSetId, textContent, ledColor, ledDuration, buzzerState, buzzerDuration, buttonInputType, rfid, gameStateCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", returnId2);
				preparedStatement.setInt(1, gameInfo.getGameId());
				preparedStatement.setInt(2, info.getTeamId());
				preparedStatement.setInt(3, info.getPlayerId());
				preparedStatement.setInt(4, 0);
				preparedStatement.setString(5, info.getText());
				preparedStatement.setString(6, "");
				preparedStatement.setInt(7, 0);
				preparedStatement.setBoolean(8, false);
				preparedStatement.setInt(9, 0);
				preparedStatement.setString(10,  info.getButtonInputType());
				preparedStatement.setString(11, "0");
				preparedStatement.setInt(12, info.getGameStateCount());
				preparedStatement.executeUpdate();
				resultSet = preparedStatement.getGeneratedKeys();
				if(resultSet.next()) { info.setGameStateId((int)resultSet.getLong(1)); }
				resultSet.close();
				preparedStatement.close();
				
				int buttonCount = 0;
				for(int i = 0; i < info.getButtons().size(); i++) {
					preparedStatement = accessor.GetConnection().prepareStatement("INSERT INTO gameStateTransitions (gameStateId, singlePushButtonColor, fourButtonPush0, fourButtonPush1, fourButtonPush2, fourButtonPush3, nextGameStateTransition) VALUES (?, ?, ?, ?, ?, ?, ?)");
					preparedStatement.setInt(1, info.getGameStateId());
					preparedStatement.setInt(2, buttonCount);
					preparedStatement.setString(3, "");
					preparedStatement.setString(4, "");
					preparedStatement.setString(5, "");
					preparedStatement.setString(6, "");
					preparedStatement.setInt(7, info.getButtons().get(i));
					preparedStatement.executeUpdate();
					preparedStatement.close();
					if(buttonCount == 3) { buttonCount = 0; } else { buttonCount++; }
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
