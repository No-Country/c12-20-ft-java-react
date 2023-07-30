/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      fontSize: {
        xsm: '0.90rem'
      },

      colors: {

        zinc: {
          750: '#2f2f31' 
        }

      },
    },
  },
  plugins: [],
}

