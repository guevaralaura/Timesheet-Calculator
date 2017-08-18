import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller implements Initializable{

    @FXML
    private TextField start;

    @FXML
    private TextField len;

    @FXML
    private TextField end;

    @FXML
    private Button calculateBtn;

    @FXML
    private Label errorMsg;

    @FXML
    void handleCalculateBtn(ActionEvent event) throws Exception{
    	//get vars
    	String startStr = start.getText();
    	String endStr = end.getText();
    	String lenStr = len.getText();
    	//HashMap<String, Boolean> map = new HashMap<String, Boolean>();
    	
    	//check only one is clear
    	int count = 0;
    	String empty = "";
    	if(startStr.isEmpty()){
    		empty = "start";
    		count++;
    	}
    	if(endStr.isEmpty()){
    		empty = "end";
    		count++;
    	}
    	if(lenStr.isEmpty()){
    		empty = "len";
    		count++;
    	}

    	if(count>1){
    		errorMsg.setVisible(true);
    		return;
    	}else if(count<1){
        	start.clear();
        	end.clear();
        	len.clear();
        	start.setEditable(true);
        	end.setEditable(true);
        	len.setEditable(true);
    		
    		return;
    	}
 
    	if(!empty.equals("len") && !lenStr.contains("\\.")){	
			try{
				if(Float.valueOf(lenStr)==null){
					len.setText("Incorrect input");
    				return;
				}
			}catch(Exception name){
				len.setText("Incorrect input");
				return;
			}
			
		}
	
		String[] splitArr;
    	if(empty.equals("end")){
    		splitArr = startStr.split(":");
    		end.setText(calculateEnd(Integer.valueOf(splitArr[0]), Integer.valueOf(splitArr[1]), Float.valueOf(lenStr)));
    	}else if (empty.equals("start")){
    		splitArr = endStr.split(":");
    		start.setText(calculateStart(Integer.valueOf(splitArr[0]), Integer.valueOf(splitArr[1]), Float.valueOf(lenStr)));
    	}else{
    		splitArr = startStr.split(":");
    		String[] splitArr2 = endStr.split(":");
    		int endingHour = Integer.valueOf(splitArr2[0]).intValue();
    		if(Integer.valueOf(splitArr2[0]) < Integer.valueOf(splitArr[0])){
    			endingHour = endingHour +12;
    		}
    		len.setText(calculateLen(Integer.valueOf(splitArr[0]), Integer.valueOf(splitArr[1]), endingHour, Integer.valueOf(splitArr2[1])));
    	}
    	start.setEditable(false);
    	end.setEditable(false);
    	len.setEditable(false);
    }

    public static String calculateStart(int hour1, int minute1, float duration){
    	int hour0 = hour1 - (int)(duration);
		int durationMins = getDurationMins(duration);
		int minute0 = minute1 - 30 - durationMins;
		while(minute0<0){
			hour0--;
			minute0+=60;
		}
		if(String.valueOf(minute0).length()==1){
			return hour0+":0"+minute0;
		}
		return hour0+":"+minute0;
    	
    }
    public static String calculateLen(int hour0, int minute0, int hour1, int minute1){
		int hour = hour1-hour0;
		int minutes = minute1-minute0-30;
		while(minutes<0){
			hour--;
			minutes = 60 - Math.abs(minutes) ;
		}
		minutes = (int) ((int) minutes*1.667);
		if(String.valueOf(minutes).length()==1){
			return hour+".0"+minutes;
		}
    	return hour+"."+minutes;
    	
    }
	public static String calculateEnd(int hour0, int minute0, float duration){
		int hour1 = hour0 + (int)(duration);
		int durationMins = getDurationMins(duration);
		int minute1 = minute0 + 30 + durationMins;
		while(minute1>=60){
			hour1++;
			minute1-=60;
		}
		if(String.valueOf(minute1).length()==1){
			return hour1+":0"+minute1;
		}
		return hour1+":"+minute1;
		
	}
	public static int getDurationMins(float duration){
		String str = String.valueOf(duration);
		String[] arr = str.split("\\.");
		String number = arr[1];
		if(number.length()!=2){
			number= number+"0";
		}
		return (int) ((int) Integer.valueOf(number) / 1.667);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errorMsg.setVisible(false);
	}

}
