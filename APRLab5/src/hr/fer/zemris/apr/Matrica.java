package hr.fer.zemris.apr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Matrica {
	private int rows, columns;
	private double[][] data;
	
	public Matrica(double[][] matrix) {
		rows = matrix.length;
		columns = matrix[0].length;
		this.data = matrix.clone();
	}
	
	public Matrica(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		data = new double[rows][columns];
	}
	
	public Matrica(String file) {
		try {
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line = br.readLine();
		String[] parts = line.split(" ");
		this.columns = parts.length;
		this.rows = 0;
		
		while(line != null) {
			rows++;
			line = br.readLine();
		}
		
		data = new double[rows][columns];
		br.close();
		br = new BufferedReader(new FileReader(file));
		int i = 0;
		while(br.ready()) {
			line = br.readLine();
			int j = 0;
			parts = line.split(" ");
			for(String part : parts) {
				data[i][j++] = Double.parseDouble(part);
			}
			i++;			
		}
		br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void print() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	public void write(String file) {
		File f = new File(file);
		try {
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {
					bw.write(data[i][j] + " ");
				}
				bw.write("\n");
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public double get(int i, int j) {
		return data[i - 1][j - 1];
	}
	
	public void set(int i, int j, double value) {
		data[i - 1][j - 1] = value;
	}

	public Matrica scalarMultiply(double scalar) {
		double[][] newData = new double[this.rows][this.columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				newData[i][j] = this.data[i][j] * scalar;
			}
		}
		Matrica multiplied = new Matrica(newData);
		return multiplied;
	}
	
	public Matrica add(Matrica other) {
		if(other.rows != this.rows || other.columns != this.columns) {
			System.out.println("Dimenzije matrica se ne poklapaju!");
			return null;
		}
		Matrica sum = new Matrica(rows, columns);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				sum.data[i][j] = this.data[i][j] + other.data[i][j];
			}
		}
		return sum;
	}
	
	public Matrica subtract(Matrica other) {
		if(other.rows != this.rows || other.columns != this.columns) {
			System.out.println("Dimenzije matrica se ne poklapaju!");
			return null;
		}
		Matrica subtracted = new Matrica(rows, columns);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				subtracted.data[i][j] = this.data[i][j] - other.data[i][j];
			}
		}		
		return subtracted;
	}
	
	public Matrica transpose() {
		Matrica transposed = new Matrica(columns, rows);
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				transposed.data[j][i] = this.data[i][j];
			}
		}
		return transposed;
	}
	
	public Matrica multiply(Matrica other) { // mnozenje ove matrice s drugom
		if(this.columns != other.rows) {
			System.out.println("Dimenzije matrica se ne poklapaju!");
			return null;
		}
		
		Matrica multiplied = new Matrica(this.rows, other.columns);
		
        for (int i = 0; i < multiplied.rows; i++)
            for (int j = 0; j < multiplied.columns; j++)
                for (int k = 0; k < this.columns; k++)                	
                	multiplied.data[i][j] += (this.data[i][k] * other.data[k][j]);
                    
        return multiplied;
	}
	
	public Matrica getColumn(int num) {
		Matrica column = new Matrica(this.rows, 1);
		
		for(int i = 0; i < this.rows; i++) {
			column.data[i][0] = this.data[i][num];
		}
		return column;
	}
	
	public Matrica setColumn(int column, Matrica newColumn) {
		for(int i = 0; i < this.rows; i++) {
			this.data[i][column] = newColumn.data[i][0];
		}
		
		return this;
	}
	
	public Matrica getRow(int num) {
		Matrica row = new Matrica(1, this.columns);
		
		for(int i = 0; i < this.columns; i++) {
			row.data[0][i] = this.data[num][i];
		}
		return row;
	}
	
	public Matrica setRow(int row, Matrica newRow) {
		for(int i = 0; i < this.columns; i++) {
			this.data[row][i] = newRow.data[0][i];
		}		
		return this;
	}
	
	public Matrica swapRows(int first, int second) {
		Matrica row1 = this.getRow(first);
		Matrica row2 = this.getRow(second);
		
		this.setRow(second, row1);
		this.setRow(first, row2);
		
		return this;
	}
	
	public boolean isSquare() {
		if(this.rows == this.columns) return true;
		return false;
	}
	
	public boolean isEqual(Matrica other) {
		if(this.rows != other.rows || this.columns != other.columns) {
			return false;
		}
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				if(Math.abs(this.data[i][j] - other.data[i][j]) > 1E-9)
					return false;
			}
		}		
		return true;
	}
	
	public Matrica supUnatrag(Matrica y) {
		for(int i = this.rows - 1; i >= 0; i--) {
			y.data[i][0] /= this.data[i][i];
			for(int j = 0; j < i; j++) {
				y.data[j][0] -= this.data[j][i] * y.data[i][0];
			}
		}
		
		return y;
	}
	
	public Matrica supUnaprijed(Matrica b) {
		int N = this.rows;
		
		for(int i = 0; i < N - 1; i++) {
			for(int j = i + 1; j < N; j++) {
				b.data[j][0] -= this.data[j][i] * b.data[i][0];
			}
		}
		
		return b;
	}
	
	public Matrica LUP(boolean useP, Matrica P, int[] rowSwaps, double eps) {
		if(!this.isSquare()) {
			System.out.println("LUP dekompozicija moguća je jedino na kvadratnim matricama.");
			return null;
		}
		Matrica LU = this.copy();
		int N = this.columns;
		
		if(P == null) {
			P = new Matrica(N, N);
		}
		
		if(useP) {
			
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(i == j) {
						P.data[i][j] = 1;
					} else {
						P.data[i][j] = 0;
					}
				}
			}
			
			for(int i = 0; i < N; i++) {
				Matrica column = LU.getColumn(i);
				int R = column.findRowWithMax(i, eps);
				
				if(Math.abs(LU.data[R][i]) < eps) {
					//System.out.println("Matrica je singularna. Ne može se riješiti");
					return null;
				}
				
				if(i < N - 1) {				
					LU.swapRows(R, i);
					P.swapRows(R, i);
					rowSwaps[0]++;
				}
				
				for(int j = i + 1; j < N; j++) {
					LU.data[j][i] /= LU.data[i][i];
					for(int k = i + 1; k < N; k++) {
						LU.data[j][k] -= LU.data[j][i] * LU.data[i][k];
					}
				}
			}
			
			return LU;
			
		} else {
			for(int i = 0; i < N - 1 ; i++) {
				for(int j = i + 1; j < N; j++) {
					if(Math.abs(LU.data[i][i]) < eps) {
						System.out.println("Stožerni element je približno 0. Nemoguće riješiti LU dekompozicijom.");
						return null;
					}
					LU.data[j][i] /= LU.data[i][i];
					for(int k = i + 1; k < N; k++) {
						LU.data[j][k] -= LU.data[j][i] * LU.data[i][k];
					}
				}
			}
			return LU;
		}
	}
	
	private int findRowWithMax(int startPoint, double eps) {
		double max = 0;
		
		for(int i = startPoint; i < this.rows; i++) {
			if(Math.abs(this.data[i][0]) > max) {
				max = Math.abs(this.data[i][0]);
			}
		}
		for(int i = startPoint; i < this.rows; i++) {
			if(Math.abs(max - Math.abs(this.data[i][0])) < eps) {
				return i;
			}
		}
		return 0;
	}

	public Matrica inv() {
		int N = this.rows;
		
		Matrica inv = new Matrica(N, N);
		
		Matrica P = new Matrica(N,N);
		
		int[] rowSwaps = {0};
		Matrica LU = this.LUP(true, P, rowSwaps, 1E-9);
		if(LU == null) {
			return null;
		}
		
		Matrica E = new Matrica(N, N);
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(i == j) {
					E.data[i][j] = 1;
				} else {
					E.data[i][j] = 0;
				}
			}
		}
		
		for(int i = 0; i < N; i++) {
			inv.setColumn(i, LU.supUnatrag(LU.supUnaprijed(E.getColumn(i))));
		}		
		
		return inv.multiply(P);
	}
	
	public double det() {
		int N = this.rows;
		
		Matrica P = new Matrica(N, N);
		
		int[] rowSwaps = {0};
		Matrica LU = this.LUP(true, P, rowSwaps, 1E-9);
		
		if(LU == null) {
			System.out.println("Nemoguce pronaci determinantu pomocu LUP dekompozicije.");
			return 0;
		}
		
		double sum = 1;
		for(int i = 0; i < N; i++) {
			sum *= LU.data[i][i];
		}
		
		return sum * Math.pow(-1, rowSwaps[0]);
	}
	
	public Matrica copy() {
		int M = this.rows;
		int N = this.columns;
		Matrica copy = new Matrica(M, N);
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < N; j++) {
				copy.data[i][j] = this.data[i][j];
			}
		}
		return copy;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public double vectorNorm() {
		double sum = 0;
		for(double[] row : data) {
			for(double value : row) {
				sum += value*value;
			}
		}
		return Math.sqrt(sum);
	}
	
	public static Matrica solveSystem(Matrica A, Matrica b) {	
		Matrica P = new Matrica(A.getRows(), A.getColumns());
		Matrica LU = A.LUP(true, P, new int[] {0}, 1E-6);
		
		Matrica y = LU.supUnaprijed(P.multiply(b));
		Matrica x = LU.supUnatrag(y);
		
		return x;
	}
	
	public static Matrica unitMatrix(int rows, int columns) {
		double[][] unit = new double[rows][columns];
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(i == j) unit[i][j] = 1;
				else unit[i][j] = 0;
			}
		}
		
		Matrica unitMatrix = new Matrica(unit);
		return unitMatrix;
	}
	
	public Matrica insertColumnOfOnes() {
		Matrica newMatrix = new Matrica(this.rows, this.columns + 1);
		
		for(int i = 1; i <= newMatrix.rows; i++) {
			for(int j = 1; j <= newMatrix.columns; j++) {
				if(j == 1) newMatrix.set(i, j, 1);
				else newMatrix.set(i, j, this.get(i, j - 1));
			}
		}		
		return newMatrix;
	}
	
	public Matrica insertRowOfOnes() {
		Matrica newMatrix = new Matrica(this.rows + 1, this.columns);
		
		for(int i = 1; i <= newMatrix.rows; i++) {
			for(int j = 1; j <= newMatrix.columns; j++) {
				if(i == 1) newMatrix.set(i, j, 1);
				else newMatrix.set(i, j, this.get(i-1, j));
			}
		}		
		return newMatrix;
	}
	
	public Matrica applySigmoid() {
		Matrica newMatrix = new Matrica(this.rows, this.columns);
		
		for(int i = 1; i <= this.rows; i++) {
			for(int j = 1; j <= this.columns; j++) {
				newMatrix.set(i, j, 1 / (1 + Math.exp(-1 * this.get(i, j))));
			}
		}
		
		return newMatrix;
	}
}
