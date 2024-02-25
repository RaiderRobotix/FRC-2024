// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;



public class limelight extends SubsystemBase {
  /** Creates a new limelight. */

  private double tx;
  private double ty;
  private double tv;
  private double area;

  // private Swerve s_swerve;
  // private Translation2d speeds;
  private NetworkTable table;

  public limelight() {

    table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry tv = table.getEntry("tv");

    this.tx = tx.getDouble(0.0);
    this.ty = ty.getDouble(0.0);
    this.area = ta.getDouble(0.0);
    this.tv = tv.getDouble(0.0);
  }


  public boolean findTarget(){
    System.out.println(tv);
    if(tv == 1){
      return true;
    }
    else{
      return false;
    }
  }

  public double getTx(){
    return this.tx;
  }

 

  public void Update_Tracking() {
    this.tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0); // Target Found
    this.tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0); // Horazontal
    this.ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0); // Vertical                                                                                    // Offset
    this.area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0); // Target Area
}

  public double estimateDistance(){
    double targetOffsetAngle_Vertical = ty;


    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = 30;//Math.arctan((goalHeightInches-limelightLensHeightInches)/); 

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeightInches = 12;

    // distance from the target to the floor
    double goalHeightInches = 51.9685039; 

    double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    //calculate distance
    double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

    System.out.println(distanceFromLimelightToGoalInches);
    return distanceFromLimelightToGoalInches;

  }

  public double getEstimatedPotValue(double getDistanceInches){
    return 0.0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    this.tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0); // Target Found
    this.tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0); // Horazontal
    this.ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0); // Vertical                                                                                    // Offset
    this.area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0); // Target Area



    SmartDashboard.putNumber("LimelightX", this.tx);
    SmartDashboard.putNumber("LimelightY", this.ty);
    SmartDashboard.putNumber("LimelightV", tv);
    SmartDashboard.putNumber("LimelightArea", this.area);
    SmartDashboard.putNumber("Limelight Distance Value", estimateDistance());
    


  }
}
