/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.LiftStickCommand;


/**
 * Add your docs here.
 */
public class LiftSystem extends Subsystem {
  // Components
  private SpeedController m_liftMotor = new VictorSP(RobotMap.PwmPorts.liftMotor);
  private DigitalInput m_limitTop = new DigitalInput(RobotMap.DioPorts.liftHigh);
  private DigitalInput m_limitBottom = new DigitalInput(RobotMap.DioPorts.liftLow);
  private Encoder m_liftEncoder = new Encoder(RobotMap.DioPorts.liftEncoderAChannel, RobotMap.DioPorts.liftEncoderBChannel,
  true, Encoder.EncodingType.k2X);


public LiftSystem(){
 double dEncDistancePerPulse = 19.25 / 360;
    m_liftEncoder.setDistancePerPulse(dEncDistancePerPulse);
}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new LiftStickCommand());
  }

  // Put methods for controlling this subsystem here. Call these from Commands.
  public void joystickLift(double speed) {
    SmartDashboard.putNumber("lift encoder", m_liftEncoder.getRaw());
   if ((speed > 0 && isAtTop()) || (speed < 0 && isAtBottom())){
    this.m_liftMotor.set(0);
   }
   else {
    this.m_liftMotor.set(speed);
   }
  }

  public void stop() {
    this.m_liftMotor.set(0);
  }

  // Supporting methods
  private boolean isAtTop() {
    return !this.m_limitTop.get();
  }

  private boolean isAtBottom() {
    return !this.m_limitBottom.get();
  }

  
}
