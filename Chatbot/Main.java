import java.io.IOException;
import java.util.List;

import org.jargp.ArgumentProcessor;
import org.jargp.BoolDef;
import org.jargp.IntDef;
import org.jargp.ParameterDef;
import org.jargp.StringDef;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.UserMentionEntity;

/*
 * Created on 2014/08/10
 */
public class Main {
	//引数処理用
    private static int count;
    private static boolean isDebug;
    private static String text;
    private static String statusid;
    private static long statusid_l = 0L;
	public static String BOT_NAME = "babymetalbot";

	//引数処理用
    private static ParameterDef[] parameterDefs = {
        new IntDef('c', "count", "number of count"),
        new BoolDef('d', "isDebug", "debug activation flag", true),
        new StringDef('t', "text", "text as message"),
        new StringDef('s', "statusid", "start status id")
    };
    public static void printFields() {
        System.out.printf("count=%d, isDebug=%b, text=%s, statusid=%s%n", count, isDebug, text, statusid);
    }


	public static void main(String[] args) throws IOException{
		//引数処理
        Main mainapp = new Main();
		ArgumentProcessor processor = new ArgumentProcessor(parameterDefs);
		processor.processArgs(args, mainapp);

		if(isDebug){
			printFields();
		}else{
	    	processor.listParameters(79, System.out);
	    	System.exit(-1);
		}
		if(!statusid.isEmpty()){
			try{
				statusid_l = Long.valueOf(statusid).longValue();
			}catch(Exception e){
				//e.printStackTrace();
		    	processor.listParameters(79, System.out);
		    	System.exit(-1);
		    }
		}

		DatabaseConnection dbconn = new DatabaseConnection();

		System.out.println(dbconn.getState());

		Chatbot chatbot = new Chatbot(BOT_NAME);

	    String response ;

	    try{
			List<Status> statuses;
			Twitterbot twitterbot = new Twitterbot();

			long lastSinceid = 501007049031839744L;
			if(statusid_l != 0){ lastSinceid = statusid_l; }
			statuses = twitterbot.getHomeTimeline( lastSinceid );
			if ( !statuses.isEmpty() ){
			    System.out.println("Showing home timeline.");
			    for (Status status : statuses) {         //取得したstatusごとのループ
			    	String name = status.getUser().getName();
			    	String screenname = status.getUser().getScreenName();
			    	String text = status.getText();
			    	long statusid = status.getId();
			    	String createdate = status.getCreatedAt().toString();

			    	if (statusid > lastSinceid){   //最新StatusIDを更新
			    		lastSinceid = status.getId();
			    	}

			    	System.out.println(name + "(" + screenname + "):" + text);
			        System.out.println("    " + statusid + " : " + createdate);

			        if (status.isRetweet()){
				    	String retscreenname = status.getRetweetedStatus().getUser().getScreenName();
				    	String rettext = status.getRetweetedStatus().getText();
			        	System.out.println("    Original-User : " + retscreenname);
			        	System.out.println("    Original-Tweet: " + rettext);
			        }

			        UserMentionEntity[] UserMentionEntities = status.getUserMentionEntities();
				    for (UserMentionEntity usermentionuntity : UserMentionEntities) {
			        	System.out.println("    UserMention: " + usermentionuntity.getScreenName() + "(" + usermentionuntity.getId() + ")");
			        }
			    	// 人工無脳の反応メッセージを取得

				    // 自身の発言は無視
				    if (BOT_NAME.equals(screenname)){
				    	System.out.println("  -->[" + BOT_NAME + ":response] (no response to my response)");
				    }else{
				    	response = chatbot.getResponse(screenname, text);
				    	// 出力
					    if ("".equals(response)){
					    	System.out.println("  -->[" + BOT_NAME + ":response] (response is null)");
					    }else{
					    	System.out.println("  -->[" + BOT_NAME + ":response] " + response);
					    }
				    	System.out.println(" ");
				    }
			    }

			}
		}catch(TwitterException te){
			te.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

/*
 	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	    String input;

	    while( true ){
		    // 入力メッセージを取得
	    	System.out.print("あなた: ");
	    	input = br.readLine();

	    	if ( input.length()==0 ) break;

	    	// 入力文字コード変換
	    	String tempStr = new String(input.getBytes("UTF-8"), "UTF-8");
	    	input = tempStr;

	    	// 人工無脳の反応メッセージを取得
    		response = chatbot.getResponse("名無し", input);

	    	// 出力
	    	System.out.println(chatbot.name + ": " + response);
	    }
*/
	}
}
