    
inPosition inColour 	inTexture  vyColour  	vyTexture  uProjectionView uvoid main() {
   vyColour    = inColour;
   vyTexture   = inTexture;
   gl_Position = uProjectionView * inPosition;
}�   vyColour 	vyTexture 	uTexture0  outColor Jvoid main() {
    outColor = texture2D(uTexture0, vyTexture) * vyColour;
}�