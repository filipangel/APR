package hr.fer.zemris.apr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Matrica {
	private int rows, columns;
	private double[][] data;
	private double eps = 1E-9;
	
	public Matrica(double[][] data) {
		rows = data.length;
		columns = data[0].length;
		this.data = data.clone();
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
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public double get(int i, int j) {
		return data[i][j];
	}
	
	public void set(int i, int j, double value) {
		data[i][j] = value;
	}

	public Matrica scalarMultiply(double scalar) {
		Matrica multiplied = new Matrica(rows, columns);
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				multiplied.data[i][j] *= scalar;
			}
		}
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
	
	public Matrica addToExisting(Matrica other) {
		if(other.rows != this.rows || other.columns != this.columns) {
			System.out.println("Dimenzije matrica se ne poklapaju!");
			return null;
		}
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				this.data[i][j] = this.data[i][j] + other.data[i][j];
			}
		}
		return this;
	}
	
	public Matrica subtractFromExisting(Matrica other) {
		if(other.rows != this.rows || other.columns != this.columns) {
			System.out.println("Dimenzije matrica se ne poklapaju!");
			return null;
		}
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				this.data[i][j] = this.data[i][j] - other.data[i][j];
			}
		}
		return this;
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
		if(this.rows != other.columns) {
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
			column.set(i, 0, this.data[i][num]);
		}
		return column;
	}
	
	public Matrica setColumn(int column, Matrica newColumn) {
		for(int i = 0; i < this.rows; i++) {
			this.set(i,  column, newColumn.get(i, 0));
		}
		
		return this;
	}
	
	public Matrica getRow(int num) {
		Matrica row = new Matrica(1, this.columns);
		
		for(int i = 0; i < this.columns; i++) {
			row.set(0, i, this.data[num][i]);
		}
		return row;
	}
	
	public Matrica setRow(int row, Matrica newRow) {
		for(int i = 0; i < this.columns; i++) {
			this.set(row,  i, newRow.get(0, i));
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
				if(Math.abs(this.data[i][j] - other.data[i][j]) > eps)
					return false;
			}
		}		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(data);
		result = prime * result + Objects.hash(columns, rows);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrica other = (Matrica) obj;
		return columns == other.columns && Arrays.deepEquals(data, other.data) && rows == other.rows;
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
	
	public Matrica LUP(boolean useP) {
		if(!this.isSquare()) {
			System.out.println("LUP dekompozicija moguća je jedino na kvadratnim matricama.");
			return null;
		}
		
		if(useP) {
			
			Matrica LU = this.copy();			
			int N = LU.columns;			
			Matrica P = new Matrica(N,N);
			
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
				int R = column.findRowWithMax(i);
				
				if(Math.abs(LU.data[R][i]) < eps) {
					System.out.println("Matrica je singularna. Ne može se riješiti");
					return null;
				}
				
				if(i < N - 1) {				
					LU.swapRows(R, i);
					P.swapRows(R, i);
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
			int N = this.columns;
			Matrica LU = this.copy();
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
	
	private int findRowWithMax(int startPoint) {
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
		
		Matrica LU = this.LUP(true);
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
		
		return inv;
	}
	
	public double det() {
		return 0;
	}
	
	public Matrica copy() {
		Matrica copy = new Matrica(this.data);
		return copy;
	}
}
