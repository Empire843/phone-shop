/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        primary: {
          "50":"#eff6ff",
          "100":"#dbeafe",
          "200":"#bfdbfe",
          "300":"#93c5fd",
          "400":"#60a5fa",
          "500":"#3b82f6",
          "600":"#2563eb",
          "700":"#1d4ed8",
          "800":"#1e40af",
          "900":"#1e3a8a",
          "950":"#172554"
        },
        boxShadow: {
          'gray-shadow': '0px 4px 4px 0px #00000040',
          'black-shadow': '0px 4px 4px 0px #00000080',
          'blur': '0px 0px 4px 0px #00000080',
          'blur-btn': '0px 0px 4px 0px #00000040',
        },
        backgroundColor: {
          'primary-red': '#B5013E',
          'primary-dark-blue': '#0583D2',
          'primary-light-blue': '#B8E3FF',
          'primary-orange': '#FF9715',
        },
        colors: {
          'primary-red': '#B5013E',
          'primary-dark-blue': '#0583D2',
          'primary-light-blue': '#B8E3FF',
          'primary-orange': '#FF9715',
        },
      }
    },
    fontFamily: {
      'body': [
        'Inter',
        'ui-sans-serif',
        'system-ui',
        '-apple-system',
        'system-ui',
        'Segoe UI',
        'Roboto',
        'Helvetica Neue',
        'Arial',
        'Noto Sans',
        'sans-serif',
        'Apple Color Emoji',
        'Segoe UI Emoji',
        'Segoe UI Symbol',
        'Noto Color Emoji'
      ],
      'sans': [
        'Inter',
        'ui-sans-serif',
        'system-ui',
        '-apple-system',
        'system-ui',
        'Segoe UI',
        'Roboto',
        'Helvetica Neue',
        'Arial',
        'Noto Sans',
        'sans-serif',
        'Apple Color Emoji',
        'Segoe UI Emoji',
        'Segoe UI Symbol',
        'Noto Color Emoji'
      ]
    }
  },
  plugins: [
    require('@tailwindcss/forms'),
    require('flowbite/plugin'),

  ],
}

