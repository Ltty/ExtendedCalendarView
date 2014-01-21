package com.pocketlifestyle.calendar.util;

public final class Color {

	public enum EventColor {
		DefaultEventIcon, Red, Blue, Yellow, Purple, Green, Orange;
	}

	public static int getColor(EventColor color) {
		switch (color) {
	
		case Red:
			return 0xff4444;
		case Blue:
			return 0x33b5e5;
		case Yellow:
			return 0xffbb33;
		case Purple:
			return 0xaa66cc;
		case Green:
			return 0x99cc00;
		case Orange:
			return 0xffbb33;
		case DefaultEventIcon:
		default:
			return 0x666666;
		}
	}
}
