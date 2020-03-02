import SyntaxHighlighter from 'react-syntax-highlighter';
import hljsStyles from './hljsStyles';
import React from "react";

/**
 * A Component based on react-syntax-highlighter for android to display highlighted code.
 */
class Component extends React.PureComponent {

  /**
   * Please refer to react-syntax-highlighter's document: <br/>
   * https://www.npmjs.com/package/react-syntax-highlighter
   * @type {{wrapLines: boolean, showLineNumbers: boolean, codeString: string, startingLineNumber: number, language: string, style: {"hljs-name": {color: string}, "hljs-attribute": {color: string}, "hljs-meta": {color: string}, "hljs-subst": {color: string}, "hljs-variable": {color: string}, "hljs-template-tag": {color: string}, "hljs-comment": {color: string}, "hljs-builtin-name": {color: string}, "hljs-built_in": {color: string}, "hljs-title": {color: string}, "hljs-regexp": {color: string}, "hljs-quote": {color: string}, "hljs-symbol": {color: string}, "hljs-doctag": {fontWeight: string}, "hljs-template-variable": {color: string}, "hljs-selector-tag": {color: string}, "hljs-literal": {color: string}, "hljs-link": {color: string}, "hljs-deletion": {color: string}, "hljs-section": {color: string, fontWeight: string}, "hljs-type": {color: string}, "hljs-selector-class": {color: string}, "hljs-strong": {fontWeight: string}, "hljs-bullet": {color: string}, "hljs-number": {color: string}, "hljs-addition": {color: string}, "hljs-emphasis": {fontStyle: string}, "hljs-selector-id": {color: string}, hljs: {padding: string, overflowX: string, color: string, background: string, display: string}, "hljs-string": {color: string}, "hljs-keyword": {color: string}}}}
   */
  state = {
    codeString: "",
    language: "text",
    style: hljsStyles.arta,
    showLineNumbers: true,
    wrapLines: true,
    startingLineNumber: 1,
  };

  componentDidMount() {
    // Setup Android JavaScript Interfaces
    const that = this;
    window.setCodeString = (codeString, language) => {
      // codeString are encoded as base64 string to keep line breakers.
      const decodedData = decodeURIComponent(window.atob(codeString));
      that.setState({
        codeString: decodedData,
        language,
      })
    };
    /**
     * style name
     * @param hljsStyle
     */
    window.setStyle = hljsStyle => {
      const style = hljsStyles[hljsStyle];
      if (hljsStyle) {
        that.setState({style})
      }
    };
    window.setShowLineNumbers = showLineNumbers => {
      let b = true;
      if (showLineNumbers.toLowerCase() === 'true') {
        b = true;
      } else if (showLineNumbers.toLowerCase() === 'false') {
        b = false;
      }
      that.setState({showLineNumbers: b})
    };
    window.setStartingLineNumber = startingLineNumber => {
      if (!isNaN(startingLineNumber)) {
        that.setState({startingLineNumber:parseInt(startingLineNumber)})
      }
    };
    try {
      // noinspection JSUnresolvedVariable,JSUnresolvedFunction
      window.AndroidSyntaxHighlightView.listStyles(JSON.stringify(Object.keys(hljsStyles)));
      // noinspection JSUnresolvedVariable,JSUnresolvedFunction
      window.AndroidSyntaxHighlightView.listSupportedLanguages(JSON.stringify(SyntaxHighlighter.supportedLanguages));
      // noinspection JSUnresolvedVariable,JSUnresolvedFunction
      window.AndroidSyntaxHighlightView.componentDidMount();
    } catch (e) {
      console.error(e)
    }
  }

  render() {
    const {style} = this.state;
    return (
      <div style={{height: '100%', background: style.hljs.background}}>
        <SyntaxHighlighter
          language={this.state.language}
          style={style}
          showLineNumbers={this.state.showLineNumbers}
          wrapLines={this.state.wrapLines}
          startingLineNumber={this.state.startingLineNumber}
        >
          {this.state.codeString}
        </SyntaxHighlighter>
      </div>
    );
  }
}

export default Component;
