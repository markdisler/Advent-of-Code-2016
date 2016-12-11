import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day10Part1 {
	
	// enum for bot reporting types (if a bot will give to a bot or an output)
	public static enum Type {BOT, OUTPUTBIN, NONE};
	
	// maps for bots and outputs
	public static Map<Integer, Bot> bots = new HashMap<Integer, Bot>();
	public static Map<Integer, OutputBin> bins = new HashMap<Integer, OutputBin>();
	
	public static void main (String [] args) throws IOException {
		ArrayList<String>lines = getInput("src/inputDay10.txt"); // Pull data from file
		
		// SPLIT the bot instructions into a list of bot directives (who to report to) and value commands (what bots are given to start them off)
		ArrayList<String> botCommands = new ArrayList<String>();
		ArrayList<String> valueCommands = new ArrayList<String>();
		for (String action : lines) {
			String[] tokens = action.split(" "); // split tokens in operation
			if (tokens[0].equals("bot")) {
				botCommands.add(action);
			} else {
				valueCommands.add(action);
			}
		}
		
		// INFORM the bots on what to report to (other bot or output) for their low and high values
		for (String botAction : botCommands) {
			String[] tokens = botAction.split(" ");
			int botID = Integer.parseInt(tokens[1]);
			Bot bot = bots.get(botID);
			if (bot == null) {  // create bin if it doesn't exist
				bot = new Bot(botID);
				bots.put(botID, bot);
			}
			int lowID = Integer.parseInt(tokens[6]);  // get id for low value
			int highID = Integer.parseInt(tokens[11]);  // get id for high value
			Type lowType = tokens[5].equals("bot") ? Type.BOT : Type.OUTPUTBIN;  // get type to report to for low value
			Type highType = tokens[10].equals("bot") ? Type.BOT : Type.OUTPUTBIN; // get type to report to for high value
			bot.setMinReporting(lowID, lowType);  // inform the bot on what to do with its LOWER value
			bot.setMaxReporting(highID, highType); // inform the bot on what to do with its HIGHER value
		}
				
		for (String valueAction : valueCommands) { 
			String[] tokens = valueAction.split(" ");
			int value = Integer.parseInt(tokens[1]);  // get the value
			int botID = Integer.parseInt(tokens[5]);  // get the bot to give the value to
			
			Bot bot = bots.get(botID);  // get the bot
			if (bot == null) {  // if it doesn't exist, create it
				bot = new Bot(botID);
				bots.put(botID, bot);
			}
			
			bot.give(value);  // give the bot the value
			
			if (bot.isFull()) {  // if the bot is now full, get the bot to start working
				evaluateBot(bot);
			}
		}
	}
	
	public static void evaluateBot(Bot bot) {
		int lowID = bot.minID(); // get the ID of the bin or output to put the LOWER value
		int highID = bot.maxID(); // get the ID of the bin or output to put the HIGHER value
		Type lowType = bot.minType(); // get the type (bin/output) that the bot should give the LOWER value to
		Type highType = bot.maxType(); // get the type (bin/output) that the bot should give the HIGHER value to
		
		if (lowType == Type.BOT) {  // bot should give LOWER value to other BOT
			Bot lowBot = bots.get(lowID);
			if (lowBot == null) { // bot doesn't exist, so create it
				bot = new Bot(lowID);
				bots.put(lowID, bot);
			}
			
			lowBot.give(bot.takeLower());  // give bot the LOWER value
			if (lowBot.isFull()) {  // if this transfer causes this bot to be full, get that bot to take action
				evaluateBot(lowBot);
			}
		} else if (lowType == Type.OUTPUTBIN) {  // bot should give LOWER value to an OUTPUT bin
			OutputBin lowOutput = bins.get(lowID);
			if (lowOutput == null) {
				lowOutput = new OutputBin(lowID);
				bins.put(lowID, lowOutput);
			}
			lowOutput.give(bot.takeLower()); // give output the LOWER value
		}
		
		if (highType == Type.BOT) {
			Bot highBot = bots.get(highID);
			if (highBot == null) {
				bot = new Bot(highID);
				bots.put(highID, bot);
			}
			
			highBot.give(bot.takeHigher());
			
			if (highBot.isFull()) {
				evaluateBot(highBot);
			}
		} else if (highType == Type.OUTPUTBIN) {
			OutputBin highOutput = bins.get(highID);
			if (highOutput == null) {
				highOutput = new OutputBin(highID);
				bins.put(highID, highOutput);
			}
			highOutput.give(bot.takeHigher());
		}
	}
				
	public static class OutputBin {
		private int value1 = -1, value2 = -1, binID = -1;
		
		public OutputBin(int binID) {
			this.binID = binID;
		}
		
		public void give(int value) {
			if (value1 == -1) 	    value1 = value;
		    else if (value2 == -1)  value2 = value;
		}
	}
	
	public static class Bot {
		private int reportMinToID = -1, reportMaxToID = -1;
		private Type reportMinToType = Type.NONE, reportMaxToType = Type.NONE;
		private int value1 = -1, value2 = -1, botID = -1;
		
		public Bot(int botID) {
			this.botID = botID;
		}
		
		public void setMinReporting(int minID, Type minType) {
			reportMinToID = minID;
			reportMinToType = minType;
		}
		
		public void setMaxReporting(int maxID, Type maxType) {
			reportMaxToID = maxID;
			reportMaxToType = maxType;
		}
		
		public int minID()    { return reportMinToID;   }
		public int maxID()    { return reportMaxToID;   }
		public Type minType() { return reportMinToType; }
		public Type maxType() { return reportMaxToType; }
		
		public void give(int value) {
			if (value1 == -1)  		value1 = value;
		    else if (value2 == -1)  value2 = value;
		}
		
		public int takeHigher() {
			int val = -1;
			
			if ((value1 == 61 && value2 == 17) || (value1 == 17 && value2 == 61)) {
				System.out.println(botID);  // this is the answer
			}
			
			if (value1 > value2) {
			    val = value1;
				value1 = -1;
			} else if (value2 > value1) {
				val = value2;
				value2 = -1;
			}
			return val;
		}
		
		public int takeLower() {
			int val = -1;
			
			if ((value1 == 61 && value2 == 17) || (value1 == 17 && value2 == 61)) {
				System.out.println(botID);   // this is the answer
			}
			
			if (value1 < value2) {
			    val = value1;
				value1 = -1;
			} else if (value2 < value1) {
				val = value2;
				value2 = -1;
			}
			return val;
		}
		
		public boolean isFull() {
			return value1 != -1 && value2 != -1;
		}
	}
	
	
	public static ArrayList<String> getInput(String p) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(p));
		ArrayList<String> list = new ArrayList<String>();
		String str = null;
		while((str = br.readLine()) != null){
			list.add(str);
		}
		br.close();
		return list;
	}
	
}
