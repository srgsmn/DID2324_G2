#ifndef Locker_h
#define Locker_h
#include "Arduino.h"
#include "Servo.h"
#include "CustomServo.h"
#include "MagneticSensor.h"
#include "CustomTimer.h"
class Locker {
  public:
    Locker(CustomServo& servo, CustomServo& bumpServo, MagneticSensor& sensor, char id);
    void send_input(char com);
    void update();
    void init();
    enum state {
      invalid,
      openUnlock,
      open,
      openBump,
      closed
    };
    char _id;
  private:
    CustomServo& _servo;
    CustomServo& _bumpServo;
    MagneticSensor& _sensor;
    state _currentState;
    char _com;
    bool _input_sent;
    bool _closeEnabled;
    void _openUnlock_enter();
    void _openUnlock_update();
    void _openUnlock_exit();
    void _openBump_enter();
    void _openBump_update();
    void _openBump_exit();
    void _open_enter();
    void _open_update();
    void _open_exit();
    void _closed_enter();
    void _closed_update();
    void _closed_exit();
    void _change_state(state nextState);
};
#endif
  
