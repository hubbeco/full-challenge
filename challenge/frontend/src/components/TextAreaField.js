import React from 'react';

function TextAreaField({ label, name, value, onChange, required }) {
  return (
    <div>
      <label>{label}:</label>
      <textarea name={name} value={value} onChange={onChange} required={required} />
    </div>
  );
}

export default TextAreaField;
