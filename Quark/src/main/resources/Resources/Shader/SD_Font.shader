    
inPosition inColour 	inTexture  vyColour 	vyTexture  uProjectionView �void main() {
   vyColour    = inColour;
   vyColour.a  = vyColour.a * (255.0 / 254.0);
   vyTexture   = inTexture;
   gl_Position = uProjectionView * inPosition;
}�   	vyTexture vyColour 	uTexture0 uSmooth  uOutline  uOutlineColour  outColor�void main() {
   if (uSmooth > 0.0) {
       float edge      = 0.5 - uOutline;
       float distance  = texture2D(uTexture0, vyTexture).a;
       float alpha     = smoothstep(edge - uSmooth, edge + uSmooth, distance);
       float border    = smoothstep(0.5  - uSmooth,  0.5 + uSmooth, distance);
       outColor        = vec4( mix(uOutlineColour.rgb, vyColour.rgb, border), alpha);
   } else {
       outColor       = texture2D(uTexture0, vyTexture) * vyColour;
   }
}�