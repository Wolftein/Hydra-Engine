    
inPosition inColour 	inTexture  vyColour  	vyTexture  uProjectionView uModel ~void main() {
   vyColour    = inColour;
   vyTexture   = inTexture;
   gl_Position = uProjectionView * uModel * inPosition;
}�   vyColour 	vyTexture 	uTexture0  outColor Jvoid main() {
    outColor = texture2D(uTexture0, vyTexture) * vyColour;
}�