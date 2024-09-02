package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BOLD = "\u001B[1m";
	
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_WHITEST = "\u001B[38m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	public static final String ANSI_GRAY_BACKGROUND = "\u001B[100m";
	public static final String ANSI_LIME_BACKGROUND = "\u001B[102m";

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String string = sc.nextLine();
			char column = string.charAt(0);
			int row = Integer.parseInt(string.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid vales are from a1 to h8");
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.printf("Turn : %d\n", chessMatch.getTurn());
		System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
		if (chessMatch.getCheck()) {
			System.out.println("CHECK!");
		}
	}

	public static void printBoard(ChessPiece[][] pieces) {
		String backgroundColor = ANSI_GRAY_BACKGROUND;
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(ANSI_RESET + ANSI_BLACK_BACKGROUND + ANSI_WHITE + (8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(backgroundColor, pieces[i][j], false);
				backgroundColor = backgroundColor.equals(ANSI_GRAY_BACKGROUND) ? ANSI_WHITE_BACKGROUND
						: ANSI_GRAY_BACKGROUND;
			}
			backgroundColor = backgroundColor.equals(ANSI_GRAY_BACKGROUND) ? ANSI_WHITE_BACKGROUND
					: ANSI_GRAY_BACKGROUND;
			System.out.println();

		}
		System.out.println(ANSI_RESET + ANSI_BLACK_BACKGROUND + ANSI_WHITE + "  a b c d e f g h " + ANSI_RESET);
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		String backgroundColor = ANSI_GRAY_BACKGROUND;
		for (int i = 0; i < pieces.length; i++) {
			System.out.print(ANSI_RESET + ANSI_BLACK_BACKGROUND + ANSI_WHITE + (8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(backgroundColor, pieces[i][j], possibleMoves[i][j]);
				backgroundColor = backgroundColor.equals(ANSI_GRAY_BACKGROUND) ? ANSI_WHITE_BACKGROUND
						: ANSI_GRAY_BACKGROUND;
			}
			backgroundColor = backgroundColor.equals(ANSI_GRAY_BACKGROUND) ? ANSI_WHITE_BACKGROUND
					: ANSI_GRAY_BACKGROUND;
			System.out.println();

		}
		System.out.println(ANSI_RESET + ANSI_BLACK_BACKGROUND + ANSI_WHITE + "  a b c d e f g h " + ANSI_RESET);
	}

	private static void printPiece(String backgroundColor, ChessPiece piece, boolean background) {
		if (background) {
			if (piece == null) {
				if (backgroundColor.equals(ANSI_GRAY_BACKGROUND)) {
					System.out.print(ANSI_GREEN_BACKGROUND + " ");
				} else {
					System.out.print(ANSI_LIME_BACKGROUND + " ");
				}
			} else {
				if (backgroundColor.equals(ANSI_GRAY_BACKGROUND)) {
					if (piece.getColor() == Color.WHITE) {
						System.out.print(ANSI_GREEN_BACKGROUND + ANSI_WHITE + piece);
					} else {
						System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + piece);
					}
				} else {
					if (piece.getColor() == Color.WHITE) {
						System.out.print(ANSI_LIME_BACKGROUND + ANSI_WHITE + piece);
					} else {
						System.out.print(ANSI_LIME_BACKGROUND + ANSI_BLACK + piece);
					}
				}
			}
		} else {
			if (piece == null) {
				System.out.print(backgroundColor + " ");
			} else {
				if (piece.getColor() == Color.WHITE) {
					System.out.print(backgroundColor + ANSI_WHITEST + ANSI_BOLD + piece + ANSI_RESET + backgroundColor);
				} else {
					System.out.print(backgroundColor + ANSI_BLACK + piece + ANSI_RESET + backgroundColor);
				}
			}
		}
		System.out.print(" ");
	}

	private static void printCapturedPieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("Captured pieces:");
		System.out.print("White: ");
		System.out.println(ANSI_WHITE + Arrays.toString(white.toArray()) + ANSI_RESET);
		System.out.print("Black: ");
		System.out.println(ANSI_RED + Arrays.toString(black.toArray()) + ANSI_RESET);
	}
}
