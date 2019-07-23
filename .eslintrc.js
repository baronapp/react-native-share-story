module.exports = {
  extends: [
    'airbnb',
    'plugin:react-native/all',
    'prettier',
    'prettier/react',
    'plugin:prettier/recommended',
  ],
  parser: 'babel-eslint',
  env: {
    jest: true,
    browser: true,
    node: true,
    'react-native/react-native': true,
  },
  settings: {
    'import/resolver': {
      node: {
        paths: ['./', 'node_modules'],
        extensions: ['', '.json', '.js', '.jsx'],
      },
    },
  },
  plugins: ['react-native', 'react', 'prettier'],
  rules: {
    'prettier/prettier': 'error',
    'react/jsx-filename-extension': [2, { extensions: ['.js', '.jsx'] }],
    'id-length': [1, { min: 3 }], // TODO: change to 2
    'import/named': 1, // TODO: remove
    'import/no-extraneous-dependencies': 1,
    'import/no-mutable-exports': 1,
    'import/no-useless-path-segments': 1, //TODO: remove
    'import/prefer-default-export': 0,
    indent: 0, // TODO: remove
    'no-alert': 1,
    'no-case-declarations': 1, // TODO: remove
    'no-console': 1,
    'no-continue': 0,
    'no-underscore-dangle': 0,
    'no-else-return': 1, // TODO: remove
    'no-param-reassign': 1,
    'no-restricted-syntax': 1,
    'no-use-before-define': 1, // TODO: remove
    'prefer-promise-reject-errors': 0,
    'react/destructuring-assignment': 0,
    'react/forbid-prop-types': [2, { forbid: ['any'] }],
    'react/jsx-max-props-per-line': [1, { maximum: 4 }], // TODO: change to 2
    'react/jsx-one-expression-per-line': 0,
    'react/jsx-tag-spacing': 1, // TODO: remove
    'react/jsx-wrap-multilines': 1, // TODO: remove
    'react/no-access-state-in-setstate': 1, // TODO: remove
    'react/no-array-index-key': 1,
    'react/no-multi-comp': 1, // TODO: remove
    'react/no-string-refs': 1, // TODO: remove
    'react/jsx-no-bind': [1, {}], // TODO: change to 2
    'react-native/split-platform-components': 0,
    'react-native/no-inline-styles': 0,
    'react-native/no-raw-text': 0, // NOTE: prevent our custom text components from causing lint errors
    'react-native/sort-styles': 1,
    'react/no-unused-prop-types': 1,
    'react/prop-types': 1,
    'react/default-props-match-prop-types': 1,
    'react-native/no-color-literals': 1,
  },
  globals: {
    $: 'readonly', // For UA tests
    $$: 'readonly', // For UA tests
    before: 'readonly', // For UA tests
    after: 'readonly', // For UA tests
    driver: 'readonly', // For UA tests
  },
};
