package rceutils;

public class ObjDataSaver {
	public float firstPersonRotatoX=0f;
	public float firstPersonRotatoY=0f;
	public float firstPersonRotatoZ=0f;
	public float firstPersonTranslateX=0f;
	public float firstPersonTranslateY=0f;
	public float firstPersonTranslateZ=0f;
	
	public float thirdPersonRotatoX=0f;
	public float thirdPersonRotatoY=0f;
	public float thirdPersonRotatoZ=0f;
	public float thirdPersonTranslateX=0f;
	public float thirdPersonTranslateY=0f;
	public float thirdPersonTranslateZ=0f;
	
	public float scaleX=1f;
	public float scaleY=1f;
	public float scaleZ=1f;
	
	public ObjDataSaver(float firstPersonRotatoX,float firstPersonRotatoY,float firstPersonRotatoZ,float firstPersonTranslateX,float firstPersonTranslateY,float firstPersonTranslateZ,float thirdPersonRotatoX,float thirdPersonRotatoY,float thirdPersonRotatoZ,float thirdPersonTranslateX,float thirdPersonTranslateY,float thirdPersonTranslateZ,float scaleX,float scaleY,float scaleZ) {
		this.firstPersonRotatoX=firstPersonRotatoX;
		this.firstPersonRotatoY=firstPersonRotatoY;
		this.firstPersonRotatoZ=firstPersonRotatoZ;
		this.firstPersonTranslateX=firstPersonTranslateX;
		this.firstPersonTranslateY=firstPersonTranslateY;
		this.firstPersonTranslateZ=firstPersonTranslateZ;
		
		this.thirdPersonRotatoX=thirdPersonRotatoX;
		this.thirdPersonRotatoY=thirdPersonRotatoY;
		this.thirdPersonRotatoZ=thirdPersonRotatoZ;
		this.thirdPersonTranslateX=thirdPersonTranslateX;
		this.thirdPersonTranslateY=thirdPersonTranslateY;
		this.thirdPersonTranslateZ=thirdPersonTranslateZ;
		
		this.scaleX=scaleX;
		this.scaleY=scaleY;
		this.scaleZ=scaleZ;
	}
	
}
