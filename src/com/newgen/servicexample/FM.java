package com.newgen.servicexample;


public class FM {
	public static enum TYPE{DIR,IMAGE,PDF,TXT,APK,UNKNOWN,AUDIO,VIDEO,HTML};
	public static boolean isImage(String file){
		file=file.toLowerCase();
		if(file.endsWith(".jpg")||file.endsWith(".png")||file.endsWith(".gif"))
			return true;
		return false;
	}
	public static boolean isPdf(String file){
		file=file.toLowerCase();
		if(file.endsWith(".pdf"))
			return true;
		return false;
	}
	public static boolean isTxt(String file){
		file=file.toLowerCase();
		if(file.endsWith(".txt"))
			return true;
		return false;
	}
	public static boolean isAudio(String file){
		file=file.toLowerCase();
		if(file.endsWith(".mp3")||file.endsWith(".m4a")||file.endsWith(".wav")||file.endsWith(".amr")||file.endsWith(".ogg"))
			return true;
		return false;
	}
	public static boolean isVideo(String file){
		file=file.toLowerCase();
		if(file.endsWith(".mp4")||file.endsWith(".avi")||file.endsWith(".mkv")||file.endsWith(".wmv"))
			return true;
		return false;
	}
	public static boolean isApk(String file){
		file=file.toLowerCase();
		if(file.endsWith(".apk"))
			return true;
		return false;
	}

	public static TYPE getType(String fileName){
		TYPE type=TYPE.UNKNOWN;
		if(isImage(fileName))
			type=TYPE.IMAGE;
		else if(isPdf(fileName))
			type=TYPE.PDF;
		else if(isTxt(fileName))
			type=TYPE.TXT;
		else if(isAudio(fileName))
			type=TYPE.AUDIO;
		else if(isVideo(fileName))
			type=TYPE.VIDEO;
		else if(isApk(fileName))
			type=TYPE.APK;
		return type;
	}

}
