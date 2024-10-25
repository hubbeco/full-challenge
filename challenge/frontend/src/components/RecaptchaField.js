import React from 'react';
import ReCAPTCHA from 'react-google-recaptcha';

function RecaptchaField({ sitekey, onChange }) {
  return (
    <div className="recaptcha-container">
      <ReCAPTCHA sitekey={sitekey} onChange={onChange} />
    </div>
  );
}

export default RecaptchaField;
