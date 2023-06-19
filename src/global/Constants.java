package global;

import javax.swing.JRadioButton;

import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;

public class Constants {
	
	public static String iconpath = "./src/icons/";
	public static String select = "Select";
	public static String imgform = ".png";
	
	public enum ETransformationStyle{
		e2Point,
		eNpoint
	}

	public enum ETools { 
		eSelection("선택",new TSelection(), ETransformationStyle.e2Point,"선택 입니다."),
		eRectangle("네모",new TRectangle(), ETransformationStyle.e2Point,"네모 입니다."), // eunm 객체생성 방법 외우기
		eOval("동그라미",new TOval(), ETransformationStyle.e2Point,"원 입니다."),
		eLine("라인",new TLine(), ETransformationStyle.e2Point,"선 입니다."),
		ePolygon("다각형",new TPolygon(), ETransformationStyle.eNpoint,"다각형 입니다.");
		//서수타입
		private String label;
		private TShape tool;
		private String tip;
		private ETransformationStyle eTransformationStyle;
		
		private ETools(String label,TShape tool, ETransformationStyle eTransformationStyle,String tip) {
			this.label = label;
			this.tool = tool;
			this.eTransformationStyle = eTransformationStyle;
			this.tip = tip;
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public String getIcon() {
			return iconpath+this.label+imgform;
		}
		
		public String getSIcon() {
			return iconpath+this.label+select+imgform;
		}
		
		public String getToolTips() {
			return tip;
		}
		
		public TShape newShape() {
			return this.tool.clone(); //객체복재
		}
		
		public ETransformationStyle getETransformationStyle() {
			return this.eTransformationStyle;
		}
	}
	
	public enum EFileMenu{
		eNew("새로 만들기"),
		eOpen("열기"),
		eSave("저장"),
		eSaveAs("다른이름으로 저장"),
		ePrint("프린트"),
		eQuit("종료");
		
		private String label;
		
		private EFileMenu(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return this.label;
		}
		
	}
	
	public enum EEditMenu{
		eUndo("되돌리기"),
		eRedo("다시실행"),
		eCopy("복사"),
		ePaste("붙여넣기"),
		eCut("자르기"),
		eDelete("삭제");
		
		private String label;
		
		private EEditMenu(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return this.label;
		}
		
	}
	
	public enum EColorMenu{
		eLine("윤곽선 선택"),
		eFill("채우기 선택"),
		eLinenow("현재 도형 선색 바꾸기"),
		eFillnow("현재 도형 채우기색 바꾸기");
		
		private String label;
		
		private EColorMenu(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return this.label;
		}
		
	}
	
}
