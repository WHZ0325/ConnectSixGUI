import javax.xml.crypto.Data;
import java.io.*;
import java.util.*;

class ProgramThread extends Thread {
	private Process process;
	private DataType data;
	private boolean success;
	ProgramThread(Process process) {
		this.process = process;
		this.success = false;
	}
	@Override
	public void run() {
		super.run();
		try {
//			System.out.println("Write");
//			System.out.println(process.getOutputStream());
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			GameData.write(out, true);
			out.close();

//			System.out.println("Read");
//			System.out.println(process.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			System.out.println("InputStream: " + bufferedReader.readLine());
			StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			data = DataType.read(stringTokenizer);
			bufferedReader.close();

//			System.out.println("End");
			success = true;
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("error in thread");
			success = false;
		}
	}

	public boolean isSuccess() { return success; }
	public DataType getData() { return data; }
}
public class ProgramController extends Player {
	private Process process = null;
	private File file;
	private boolean firstPlayer;
	ProgramController(File file, boolean firstPlayer) {
		this.file = file;
		this.firstPlayer = firstPlayer;
	}

	@Override
	public DataType getOperation() throws Exception {

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.redirectErrorStream();
		processBuilder.directory(file.getParentFile());
		if(System.getProperty("os.name").equals("Windows")) {
			processBuilder.command(file.getName());
		}
		else {
			processBuilder.command("./" + file.getName());
		}
		process = processBuilder.start();
//		System.out.println("ProcessBuilder Start");
		ProgramThread programThread = new ProgramThread(process);
		programThread.start();
//		System.out.println("Program Thread Start");
		programThread.join(1000);
		if(!programThread.isSuccess()) {
			programThread.interrupt();
			process.destroy();
			throw new Exception();
		}
		else {
//			System.out.println("Program Ends.");
			operation = programThread.getData();
		}
		return operation;
	}

}
