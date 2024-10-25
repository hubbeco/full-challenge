import React, { useRef } from 'react';
import ReCAPTCHA from 'react-google-recaptcha';

function RecaptchaField({ sitekey, onChange, recaptchaRef }) {
  return (
    <div className="recaptcha-container">
      <ReCAPTCHA
        sitekey={sitekey}
        onChange={onChange}
        ref={recaptchaRef} 
      />
    </div>
  );
}

export default RecaptchaField;
