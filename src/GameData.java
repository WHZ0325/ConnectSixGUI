import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.StringTokenizer;

class Point {
	int x, y;
	Point() {
		x = y = -1;
	}
	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	public boolean isNull() {
		return x == -1 && y == -1;
	}
	public void clear() {
		this.x = this.y = -1;
	}
	public static Point read(StringTokenizer in) {
		int x = Integer.parseInt(in.nextToken());
		int y = Integer.parseInt(in.nextToken());
		return new Point(x, y);
	}
	@Override
	public String toString() {
		return x + " " + y;
	}
}
class DataType {
	Point p0, p1;
	DataType(int x0, int y0, int x1, int y1) {
		this.p0 = new Point(x0, y0);
		this.p1 = new Point(x1, y1);
	}
	DataType(Point p0, Point p1) {
		this.p0 = p0;
		this.p1 = p1;
	}
	DataType(DataType dataType) {
		this.p0 = new Point(dataType.p0);
		this.p1 = new Point(dataType.p1);
	}
	public static DataType read(StringTokenizer in) {
		Point p0 = Point.read(in);
		Point p1 = Point.read(in);
		return new DataType(p0, p1);
	}
	@Override
	public String toString() {
		return p0 + " " + p1;
	}
}
public class GameData {
	private static int round = 0, placedCount = 0;
	private static int map[][] = new int[15][15];
	private static List<DataType> dataList = new ArrayList<>();
	public static int getRound() { return round; }
	public static int getCurrentTurn() { return 1 - (round & 1); }
	private static int getPoint(Point p) { return map[p.x][p.y]; }
	public static boolean available(Point p) { return getPoint(p) == 0; }
	public static int check() {
		for(int i = 0; i < 15; ++i) {
			for(int j = 0; j < 15; ++j) if(!available(new Point(i, j))) {
				if(i + 5 < 15) {
					boolean same = true;
					for(int k = 1; k <= 5; ++k) {
						if(map[i][j] != map[i + k][j]) {
							same = false;
							break;
						}
					}
					if(same) return map[i][j];
				}
				if(j + 5 < 15) {
					boolean same = true;
					for(int k = 1; k <= 5; ++k) {
						if(map[i][j] != map[i][j + k]) {
							same = false;
							break;
						}
					}
					if(same) return map[i][j];
				}
				if(i + 5 < 15 && j + 5 < 15) {
					boolean same = true;
					for(int k = 1; k <= 5; ++k) {
						if(map[i][j] != map[i + k][j + k]) {
							same = false;
							break;
						}
					}
					if(same) return map[i][j];
				}
				if(i + 5 < 15 && j - 5 >= 0) {
					boolean same = true;
					for(int k = 1; k <= 5; ++k) {
						if(map[i][j] != map[i + k][j - k]) {
							same = false;
							break;
						}
					}
					if(same) return map[i][j];
				}
			}
		}
		return placedCount == 225 ? 0 : -1;
	}
	public static void nextRound() { ++round; }
	public static void place(Point p, int player) {
		map[p.x][p.y] = player + 1;
	}
	public static void insert(DataType dataType) {
		System.out.println("Insert: " + dataType); dataList.add(new DataType(dataType));
	}
	public static void write(BufferedWriter out, boolean first) throws IOException {
		out.write(String.format("%d\n", round));
		System.out.println("Write to program: ");
		System.out.println(String.format("%d", round));
		for(DataType data: dataList) {
			if(data.p0.isNull() && !first) continue;
			out.write(data + "\n");
			System.out.println(data);
		}
	}

	public static void init() {
		round = placedCount = 0; dataList.clear();
		insert(new DataType(-1, -1, -1, -1));
		for(int i = 0; i < 15; ++i) {
			for(int j = 0; j < 15; ++j) {
				map[i][j] = 0;
			}
		}
	}
}
