/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx}",
  ],
  plugins: [require("daisyui")],
  theme: {
    extend: {},
  },
  daisyui: {
    themes: [
      {
        mytheme: {

          "primary": "#F00",

          "secondary": "#fff",

          "accent": "#fff",

          "neutral": "#1d232a",

          "base-100": "#111",

          "info": "#fff",

          "success": "#36d399",

          "warning": "#fbbd23",

          "error": "#f00",
        },
      },
    ],
  },
}