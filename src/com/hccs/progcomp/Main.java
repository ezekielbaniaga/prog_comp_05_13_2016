package com.hccs.progcomp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Main {
	
	private static File f, fout;
	private static BufferedReader reader;
	private static FileWriter fw;
	
	private static int nMin, nMax, mMin, mMax;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {
        f = new File("D:/prog comp/A-small-practice.in");
//        f = new File("D:/prog comp/A-large-practice.in");
        fout = new File("D:/prog comp/lorelie_jeretth_ezekiel_Small.txt");
//        fout = new File("D:/prog comp/lorelie_jeretth_ezekiel_Large.txt");
        reader = new BufferedReader( new InputStreamReader( new FileInputStream( f )) );
        fw = new FileWriter( fout );

        if (f.length() > 100 * 1024) {
        	fw.write( "File input is longer than 100KB" );
        	System.out.println( "File input is longer than 100KB" );

        	fw.flush();
        	fw.close();
        	System.exit( 0 );
        }

		int t, n, m; // primary vars
		String nm;

		//small dataset
		nMin = 0; nMax = 10;
		mMin = 1; mMax = 10;

		//large dataset
//		nMin = 0; nMax = 100;
//		mMin = 1; mMax = 100;


		// Get # of test cases
		t = getInt();

		if ( !validateNumTestCase( t ) ) {
			
			String message = "Number of Test Case is invalid";
			fw.write( message );
        	System.out.println( message );

        	fw.flush();
        	fw.close();
        	System.exit( 0 );
		}

		List<String> caseResult = new ArrayList<>();

		// main loop
		for ( int c = 1; c <= t; c++ ) {
			nm = get();

			// validation

			String[] nmArr = nm.split( " " );

			n = Integer.valueOf( nmArr[0] );
			m = Integer.valueOf( nmArr[1] );

			// validation
			if (!validateN( n )) {
                String message = "N input is invalid";
                fw.write( message );
                System.out.println( message );

                fw.flush();
                fw.close();
                System.exit( 0 );
			}
			
			if (!validateM( m )) {
                String message = "M input is invalid";
                fw.write( message );
                System.out.println( message );

                fw.flush();
                fw.close();
                System.exit( 0 );
			}


			// path input
			Set<String> existingDir = new HashSet<>();
			List<String> pathsM = new ArrayList<>();

			for ( int d = 0; d < n; d++ ) {
				String inp = replaceLastSlash( get() );

                if (!validatePath( inp )) {
                    String message = "Path length is > 100";
                    fw.write( message );
                    System.out.println( message );

                    fw.flush();
                    continue;
//                    fw.close();
//                    System.exit( 0 );
                }

				addToExisting( inp, existingDir );
			}

			for ( int e = 0; e < m; e++ ) {
				String inp = replaceLastSlash( get() );

                if (!validatePath( inp )) {
                    String message = "Path length is > 100";
                    fw.write( message );
                    System.out.println( message );

                    fw.flush();
                    continue;
//                    fw.close();
//                    System.exit( 0 );
                }

				pathsM.add( inp );
			}

			// algorithm

			int mkdirCount = 0;
			
			 for (String pathMEach : pathsM) {
				 mkdirCount += countToExisting( pathMEach, existingDir );
			 }
			
			// counting
			caseResult.add( String.format( "Case #%d: %d", c, mkdirCount ) );
		}


		System.out.println( "\n\nOutput:" );
		for ( String str : caseResult ) {
			System.out.println( str );
			fw.write( str + "\n" );
		}
		
		fw.flush();
		fw.close();
	}
	
	private static int countToExisting(String input, Set<String> set) {
		String[] inp = input.split( "/" );

		int count=0;

        for (int i=1; i<inp.length; i++) {
			String fullpath = "";
			
			for (int j=1; j<=i; j++) {
				fullpath = fullpath + inp[j] + "/";
			}

			fullpath = replaceLastSlash( fullpath );
			if (!set.contains( fullpath )) {
				set.add( fullpath );
				count++;
			}
		}

        return count;
	}
	
	private static void addToExisting(String input, Set<String> set) {
		String[] inp = input.split( "/" );
		
		for (int i=1; i<inp.length; i++) {
			String fullpath = "";
			
			for (int j=1; j<=i; j++) {
				fullpath = fullpath + inp[j] + "/";
			}

			set.add(replaceLastSlash( fullpath ));
		}
	}

	private static String replaceLastSlash( String input ) {
		return input.replaceAll( "/$", "" );
	}

	private static boolean validateNumTestCase( int t ) {
		return ( t >= 1 && t <= 100 );
	}
	
	private static boolean validateN(int n) {
		return ( n >= nMin && n <= nMax );
	}
	
	private static boolean validateM(int m) {
		return ( m >= mMin && m <= mMax );
	}
	
	private static boolean validatePath(String path) {
		return path.length() <= 100;
	}


	private static String get() throws Exception {
//		BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
		String line = reader.readLine();

		return line;
	}


	private static int getInt() throws Exception {
		return Integer.valueOf( get() );
	}

}
