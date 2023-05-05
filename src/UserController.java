//class UserThread extends Thread {
//	private boolean success;
//	private DataType data;
//	UserThread() {
////		super();
//		success = false;
//		data = new DataType(-1, -1, -1, -1);
//	}
//
//	public boolean isSuccess() {
//		return success;
//	}
//	public DataType getData() { return data; }
//
//	@Override
//	public void run() {
//		try {
////			synchronized(GameData.sharedPoint) {
//				System.out.println("Waiting for data.");
////				GameData.sharedPoint.wait();
//				GameData.sharedPoint.start();
//				GameData.sharedPoint.join();
//				System.out.println("Got the data.");
//				data.p0 = new Point(GameData.sharedPoint.get());
//				if(GameData.getRound() > 1) {
//					System.out.println("Waiting for data.");
////					GameData.sharedPoint.wait();
//					GameData.sharedPoint.start();
//					GameData.sharedPoint.join();
//					System.out.println("Got the data.");
//					data.p1 = new Point(GameData.sharedPoint.get());
//				}
//				success = true;
////			}
//		} catch(Exception e) {
//			System.out.println("Error.");
//			success = false;
//		}
//	}
//}
public class UserController extends Player {
	private boolean firstPlayer;
	private DataType data;
	UserController(boolean firstPlayer) {
		this.firstPlayer = firstPlayer;
		this.data = new DataType(-1, -1, -1, -1);
	}
	@Override
	public DataType getOperation() throws Exception {
//		System.out.println("getOperation() in UserController");
//		playingView.enableAllButtons();
//		UserThread userThread = new UserThread();
//		userThread.start();
////		userThread.join(1000);
////		userThread.join();
//		userThread.wait();
//		if(!userThread.isSuccess()) {
//			userThread.interrupt();
//			userThread.destroy();
//			throw new Exception();
//		}
//		else {
////			System.out.println("Program Ends.");
//			operation = userThread.getData();
//		}
//		System.out.println("Waiting for data.");
//		while(GameData.sharedPoint.isNull());
//		System.out.println("Got the data.");
//		data.p0 = new Point(GameData.sharedPoint);
//		GameData.sharedPoint.clear();
//		if(GameData.getRound() > 1) {
//			System.out.println("Waiting for data.");
//			while (GameData.sharedPoint.isNull()) ;
//			System.out.println("Got the data.");
//			data.p1 = new Point(GameData.sharedPoint);
//			GameData.sharedPoint.clear();
//		}

		return operation;
	}
}
