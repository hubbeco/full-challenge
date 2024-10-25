import React, { useState, useRef } from 'react';
import InputField from '../../components/InputField';
import TextAreaField from '../../components/TextAreaField';
import RecaptchaField from '../../components/RecaptchaField';
import '../css/ContactForm.css';

function ContactForm() {
  const [formData, setFormData] = useState({
    name: '',
    mail: '',
    comment: ''
  });
  const [recaptchaToken, setRecaptchaToken] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [isSent, setIsSent] = useState(false);
  const recaptchaRef = useRef(); // Referência ao reCAPTCHA

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleRecaptchaChange = (token) => {
    setRecaptchaToken(token);
  };

  const validateForm = () => {
    const { name, mail, comment } = formData;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!name.trim()) {
      alert('O nome não pode ser vazio ou conter apenas espaços.');
      return false;
    }

    if (!emailRegex.test(mail)) {
      alert('Por favor, insira um email válido.');
      return false;
    }

    if (comment.trim().length <= 15) {
      alert('O comentário deve ter mais de 15 caracteres e não pode ser apenas espaços.');
      return false;
    }

    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) {
      return;
    }
    if (!recaptchaToken) {
      alert('Por favor, complete o reCAPTCHA.');
      return;
    }

    setIsSent(false); // Reseta a mensagem de sucesso antes de enviar um novo email
    setIsLoading(true);
    
    try {
      const response = await fetch('http://localhost:8080/api/contact', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ ...formData, recaptchaToken }),
      });
      if (!response.ok) {
        throw new Error('Captcha inválido! Refaça o captcha.');
      }
      setIsSent(true);
      recaptchaRef.current.reset(); // Reseta o reCAPTCHA após o envio do email
      setRecaptchaToken(null); // Limpa o token do reCAPTCHA
    } catch (error) {
      alert('Houve um problema com o envio: ' + error.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Formulário de Contato</h2>
      <InputField
        label="Nome"
        type="text"
        name="name"
        value={formData.name}
        onChange={handleChange}
        required
      />
      <InputField
        label="Email"
        type="email"
        name="mail"
        value={formData.mail}
        onChange={handleChange}
        required
      />
      <TextAreaField
        label="Comentário"
        name="comment"
        value={formData.comment}
        onChange={handleChange}
        required
      />
      <RecaptchaField
        // sitekey="6LfEg2sqAAAAAN-A_fHP5Jta7JMqvEqarYkyBpFk" // chave real
        sitekey="6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI" // chave teste
        onChange={handleRecaptchaChange}
        recaptchaRef={recaptchaRef} 
      />
      <div className="loading-indicator">
        {isLoading && <span>Enviando email... Por favor, aguarde.</span>}
        {isSent && <span>Email enviado com sucesso ✔️</span>}
      </div>
      <button type="submit" disabled={isLoading}>Enviar</button>
    </form>
  );
}

export default ContactForm;
