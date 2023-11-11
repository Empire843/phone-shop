/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      boxShadow: {
        'gray-shadow': '0px 4px 4px 0px #00000040',
        'black-shadow': '0px 4px 4px 0px #00000080',
        'blur': '0px 0px 4px 0px #00000080',
        'blur-btn': '0px 0px 4px 0px #00000040',
      },
      backgroundColor: {
        'primary-red': '#B5013E',
        'primary-light-red': '#CD5C5C',
        'primary-dark-pink': '#F3D9D9',
        'primary-pink': '#FFEBEB',
        'prinary-gray': '#F9F9F9',
        'prinary-dark-gray': '#BDBDBD',
        'prinary-blue': '#004B93'
      },
      borderWidth: {
        'thin': '1px',
        'medium': '2px',
        'thick': '4px',
      },
      colors: {
        'primary-red': '#B5013E',
        'primary-light-red': '#CD5C5C',
        'primary-blue': '#0B3C5D',
        'primary-yellow': '#F2B705',
        'primary-green': '#2ECC71',
        'primary-gray': '#F2F2F2',
        'primary-black': '#000000',
        'primary-dark-gray': '#BDBDBD',
        'primary-white': '#FFFFFF',
      },
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

