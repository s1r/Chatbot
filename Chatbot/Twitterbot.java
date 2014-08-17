import java.util.List;

import twitter4j.*;

public class Twitterbot {
	private Twitter twitter;

	public Twitterbot(){
		this.getInstance();
	}

	private void getInstance(){
		TwitterFactory factory = new TwitterFactory();
		twitter = factory.getInstance();
	}

//	public static void main(String[] args){
//		Twitterbot main = new Twitterbot();
//		main.twitterAPITest();
//	}

	public void twitterAPITest(){
//		long latestSinceid = 0;
		StatusUpdate statusupdate = new StatusUpdate("@s1r_h テストのツイートDEATH");
		//if (sinceid == 0){
			// InReplyToでリプライするときは本文に@UERNAMEが必須でもなさそう？
			statusupdate.setInReplyToStatusId( 500904424164696064L );
		//}
		try{
			//latestSinceid = this.getHomeTimeline( 500904424164696064L );
			//System.out.println("Last SiceID: " + latestSinceid);
			
			this.postMessage(statusupdate);
			System.out.println("Post: " + statusupdate.getStatus());
		}catch(TwitterException te){
			te.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<Status> getHomeTimeline(long sinceid) throws TwitterException{
		Paging paging = new Paging();
		paging.setSinceId(sinceid);
		List<Status> statuses = twitter.getHomeTimeline(paging);
		return statuses;
	}

	public void postMessage(StatusUpdate statusupdate ) throws TwitterException{
		try{
			twitter.updateStatus(statusupdate);
		}catch(TwitterException te){
			te.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
