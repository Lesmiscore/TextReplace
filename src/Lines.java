import java.util.*;
import java.io.*;

public class Lines
{
	public static void main(String[] args)
	{
		File replaceFilesDir=new File("/sdcard/PocketMine/plugins/UHC.src/src/nao20010128nao/block");
		for(String s:ls(replaceFilesDir)){
			System.out.println(s);
			File single=new File(replaceFilesDir,s);
			String cont=readWholeFile(single);
			if(cont==null){
				continue;
			}
			String[] lines=lines(cont);
			StringBuilder sb=new StringBuilder(lines.length*100);
			for(String l:lines){
				sb.append(l).append('\n');
			}
			sb.setLength(sb.length()-1);
			cont=sb.toString();
			System.out.println(cont);
			writeToFile(single,cont);
		}
	}
	public static String[] lines(String s){
		try {
			BufferedReader br=new BufferedReader(new StringReader(s));
			List<String> tmp=new ArrayList<>(4);
			String line=null;
			while (null != (line = br.readLine()))tmp.add(line);
			return tmp.toArray(new String[tmp.size()]);
		} catch (IOException e) {
			return new String[]{};
		}
	}
	public static boolean writeToFile(File f,String content){
		FileWriter fw=null;
		try{
			(fw=new FileWriter(f)).write(content);
			return true;
		}catch(Throwable e){
			return false;
		}finally{
			try {
				if (fw != null)fw.close();
			} catch (IOException e) {}
		}
	}
	public static String readWholeFile(File f){
		FileReader fr=null;char[] buf=new char[8192];
		StringBuilder sb=new StringBuilder(8192);
		try{
			fr=new FileReader(f);
			while(true){
				int r=fr.read(buf);
				if(r<=0){
					break;
				}
				sb.append(buf,0,r);
			}
			return sb.toString();
		}catch(Throwable e){
			return null;
		}finally{
			try {
				if (fr != null)fr.close();
			} catch (IOException e) {}
		}
	}
	
	public static String[] ls(File dir){
		List<String> ls=new ArrayList();
		try {
			ProcessBuilder pb=new ProcessBuilder()
				.command("ls",dir.getAbsolutePath())
				.redirectErrorStream(true)
				;//.directory(dir);
			pb.environment().put("PATH",dir.toString()+":"+pb.environment().get("PATH"));
			InputStream is=pb
				.start()
				.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String s;
			while(null!=(s=br.readLine()))ls.add(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ls.toArray(new String[ls.size()]);
	}
}
