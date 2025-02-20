import { pages } from 'build-time-data';
import { LitElement, css, html } from 'lit';
import 'qwc/qwc-extension-link.js';

const NAME = "Logging Sentry";
export class QwcLoggingSentryCard extends LitElement {

  static styles = css`
      .identity {
        display: flex;
        justify-content: flex-start;
      }

      .description {
        padding-bottom: 10px;
      }

      .logo {
        padding-bottom: 10px;
        margin-right: 5px;
      }

      .card-content {
        color: var(--lumo-contrast-90pct);
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        padding: 2px 2px;
        height: 100%;
      }

      .card-content slot {
        display: flex;
        flex-flow: column wrap;
        padding-top: 5px;
      }
    `;

  static properties = {
    description: { type: String }
  };

  constructor() {
    super();
  }

  connectedCallback() {
    super.connectedCallback();
  }

  render() {
    return html`<div class="card-content" slot="content">
            <div class="identity">
                <div class="logo">
                    <img src="data:image/svg+xml;base64,PHN2ZyBjbGFzcz0iX19zbnRyeV9fIGNzcy1sZmJvNmogZTFpZ2s4eDA0IiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA3MiA2NiIgd2lkdGg9IjQwMCIgaGVpZ2h0PSIzNjciIHN0eWxlPSJiYWNrZ3JvdW5kLWNvbG9yOiByZ2IoMjU1LCAyNTUsIDI1NSk7Ij48ZGVmcz48c3R5bGUgdHlwZT0idGV4dC9jc3MiPkBtZWRpYSAocHJlZmVycy1jb2xvci1zY2hlbWU6IGRhcmspIHtzdmcuX19zbnRyeV9fIHsgYmFja2dyb3VuZC1jb2xvcjogIzU4NDY3NCAhaW1wb3J0YW50OyB9cGF0aC5fX3NudHJ5X18geyBmaWxsOiAjZmZmZmZmICFpbXBvcnRhbnQ7IH19PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTI5LDIuMjZhNC42Nyw0LjY3LDAsMCwwLTgsMEwxNC40MiwxMy41M0EzMi4yMSwzMi4yMSwwLDAsMSwzMi4xNyw0MC4xOUgyNy41NUEyNy42OCwyNy42OCwwLDAsMCwxMi4wOSwxNy40N0w2LDI4YTE1LjkyLDE1LjkyLDAsMCwxLDkuMjMsMTIuMTdINC42MkEuNzYuNzYsMCwwLDEsNCwzOS4wNmwyLjk0LTVhMTAuNzQsMTAuNzQsMCwwLDAtMy4zNi0xLjlsLTIuOTEsNWE0LjU0LDQuNTQsMCwwLDAsMS42OSw2LjI0QTQuNjYsNC42NiwwLDAsMCw0LjYyLDQ0SDE5LjE1YTE5LjQsMTkuNCwwLDAsMC04LTE3LjMxbDIuMzEtNEEyMy44NywyMy44NywwLDAsMSwyMy43Niw0NEgzNi4wN2EzNS44OCwzNS44OCwwLDAsMC0xNi40MS0zMS44bDQuNjctOGEuNzcuNzcsMCwwLDEsMS4wNS0uMjdjLjUzLjI5LDIwLjI5LDM0Ljc3LDIwLjY2LDM1LjE3YS43Ni43NiwwLDAsMS0uNjgsMS4xM0g0MC42cS4wOSwxLjkxLDAsMy44MWg0Ljc4QTQuNTksNC41OSwwLDAsMCw1MCwzOS40M2E0LjQ5LDQuNDksMCwwLDAtLjYyLTIuMjhaIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgxMSwgMTEpIiBmaWxsPSIjMzYyZDU5IiBjbGFzcz0iX19zbnRyeV9fIj48L3BhdGg+PC9zdmc+"
                                       alt="${NAME}" 
                                       title="${NAME}"
                                       width="32" 
                                       height="32">
                </div>
                <div class="description">${this.description}</div>
            </div>
            ${this._renderCardLinks()}
        </div>
        `;
  }

  _renderCardLinks() {
    return html`${pages.map(page => html`
                            <qwc-extension-link slot="link"
                                extensionName="${NAME}"
                                iconName="${page.icon}"
                                displayName="${page.title}"
                                staticLabel="${page.staticLabel}"
                                dynamicLabel="${page.dynamicLabel}"
                                streamingLabel="${page.streamingLabel}"
                                path="${page.id}"
                                ?embed=${page.embed}
                                externalUrl="${page.metadata.externalUrl}"
                                webcomponent="${page.componentLink}" >
                            </qwc-extension-link>
                        `)}`;
  }

}
customElements.define('qwc-logging-sentry-card', QwcLoggingSentryCard);