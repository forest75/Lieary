import 'package:flutter/cupertino.dart';
import 'package:gradient_borders/box_borders/gradient_box_border.dart';

BoxDecoration BgThemeGradient(){
  return  BoxDecoration(
      gradient: LinearGradient(
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
          stops: [
            0.3,
            0.6,
            0.9
          ],
          colors: [
            Color(0xff0f2027),
            Color(0xff203a43),
            Color(0xff2c5364),
          ]));
}

BoxDecoration BtnThemeGradient(){
  return BoxDecoration(
      gradient: LinearGradient(colors: [
        Color(0xff79F1A4),
        Color(0xff0E5CAD),
      ]),
      borderRadius: BorderRadius.circular(25));
}

BoxDecoration BtnThemeGradientLine(){
  return BoxDecoration(
    border: GradientBoxBorder(
        gradient: LinearGradient(colors: [
          Color(0xff79F1A4),
          Color(0xff0E5CAD),
        ]),
        width: 2),
    borderRadius: BorderRadius.circular(25),
  );
}

BoxDecoration BgThemeIncludeImage(){
  return BoxDecoration(
    gradient: LinearGradient(
      begin: Alignment.topCenter,
      end: Alignment.bottomCenter,
      colors: [
        Color(0xff0F2027),
        Color(0xff203A43),
        Color(0xff2C5364),
      ],
      stops: [0, 0.4, 1.0],
    ),
    image: DecorationImage(
      image: AssetImage('assets/img/background_1.png'),
      fit: BoxFit.cover,
    ),
  );
}