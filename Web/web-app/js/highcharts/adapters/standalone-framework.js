/*
 Highcharts JS v3.0.9 (2014-01-15)

 Standalone Highcharts Framework

 License: MIT License
 */
var HighchartsAdapter = function () {
    function o(c) {
        function b(b, a, d) {
            b.removeEventListener(a, d, !1)
        }

        function d(b, a, d) {
            d = b.HCProxiedMethods[d.toString()];
            b.detachEvent("on" + a, d)
        }

        function a(a, c) {
            var f = a.HCEvents, i, g, k, j;
            if (a.removeEventListener)i = b; else if (a.attachEvent)i = d; else return;
            c ? (g = {}, g[c] = !0) : g = f;
            for (j in g)if (f[j])for (k = f[j].length; k--;)i(a, j, f[j][k])
        }

        c.HCExtended || Highcharts.extend(c, {HCExtended: !0, HCEvents: {}, bind: function (a, b) {
            var d = this, c = this.HCEvents, g;
            if (d.addEventListener)d.addEventListener(a,
                b, !1); else if (d.attachEvent) {
                g = function (a) {
                    b.call(d, a)
                };
                if (!d.HCProxiedMethods)d.HCProxiedMethods = {};
                d.HCProxiedMethods[b.toString()] = g;
                d.attachEvent("on" + a, g)
            }
            c[a] === r && (c[a] = []);
            c[a].push(b)
        }, unbind: function (c, h) {
            var f, i;
            c ? (f = this.HCEvents[c] || [], h ? (i = HighchartsAdapter.inArray(h, f), i > -1 && (f.splice(i, 1), this.HCEvents[c] = f), this.removeEventListener ? b(this, c, h) : this.attachEvent && d(this, c, h)) : (a(this, c), this.HCEvents[c] = [])) : (a(this), this.HCEvents = {})
        }, trigger: function (a, b) {
            var d = this.HCEvents[a] ||
                [], c = d.length, g, k, j;
            k = function () {
                b.defaultPrevented = !0
            };
            for (g = 0; g < c; g++) {
                j = d[g];
                if (b.stopped)break;
                b.preventDefault = k;
                b.target = this;
                if (!b.type)b.type = a;
                j.call(this, b) === !1 && b.preventDefault()
            }
        }});
        return c
    }

    var r, l = document, p = [], m = [], q, n;
    Math.easeInOutSine = function (c, b, d, a) {
        return-d / 2 * (Math.cos(Math.PI * c / a) - 1) + b
    };
    return{init: function (c) {
        if (!l.defaultView)this._getStyle = function (b, d) {
            var a;
            return b.style[d] ? b.style[d] : (d === "opacity" && (d = "filter"), a = b.currentStyle[d.replace(/\-(\w)/g, function (b, a) {
                return a.toUpperCase()
            })],
                d === "filter" && (a = a.replace(/alpha\(opacity=([0-9]+)\)/, function (b, a) {
                    return a / 100
                })), a === "" ? 1 : a)
        }, this.adapterRun = function (b, d) {
            var a = {width: "clientWidth", height: "clientHeight"}[d];
            if (a)return b.style.zoom = 1, b[a] - 2 * parseInt(HighchartsAdapter._getStyle(b, "padding"), 10)
        };
        if (!Array.prototype.forEach)this.each = function (b, d) {
            for (var a = 0, c = b.length; a < c; a++)if (d.call(b[a], b[a], a, b) === !1)return a
        };
        if (!Array.prototype.indexOf)this.inArray = function (b, d) {
            var a, c = 0;
            if (d)for (a = d.length; c < a; c++)if (d[c] === b)return c;
            return-1
        };
        if (!Array.prototype.filter)this.grep = function (b, d) {
            for (var a = [], c = 0, h = b.length; c < h; c++)d(b[c], c) && a.push(b[c]);
            return a
        };
        n = function (b, c, a) {
            this.options = c;
            this.elem = b;
            this.prop = a
        };
        n.prototype = {update: function () {
            var b;
            b = this.paths;
            var d = this.elem, a = d.element;
            b && a ? d.attr("d", c.step(b[0], b[1], this.now, this.toD)) : d.attr ? a && d.attr(this.prop, this.now) : (b = {}, b[d] = this.now + this.unit, Highcharts.css(d, b));
            this.options.step && this.options.step.call(this.elem, this.now, this)
        }, custom: function (b, c, a) {
            var e =
                this, h = function (a) {
                return e.step(a)
            }, f;
            this.startTime = +new Date;
            this.start = b;
            this.end = c;
            this.unit = a;
            this.now = this.start;
            this.pos = this.state = 0;
            h.elem = this.elem;
            h() && m.push(h) === 1 && (q = setInterval(function () {
                for (f = 0; f < m.length; f++)m[f]() || m.splice(f--, 1);
                m.length || clearInterval(q)
            }, 13))
        }, step: function (b) {
            var c = +new Date, a;
            a = this.options;
            var e;
            if (this.elem.stopAnimation)a = !1; else if (b || c >= a.duration + this.startTime) {
                this.now = this.end;
                this.pos = this.state = 1;
                this.update();
                b = this.options.curAnim[this.prop] = !0;
                for (e in a.curAnim)a.curAnim[e] !== !0 && (b = !1);
                b && a.complete && a.complete.call(this.elem);
                a = !1
            } else e = c - this.startTime, this.state = e / a.duration, this.pos = a.easing(e, 0, 1, a.duration), this.now = this.start + (this.end - this.start) * this.pos, this.update(), a = !0;
            return a
        }};
        this.animate = function (b, d, a) {
            var e, h = "", f, i, g;
            b.stopAnimation = !1;
            if (typeof a !== "object" || a === null)e = arguments, a = {duration: e[2], easing: e[3], complete: e[4]};
            if (typeof a.duration !== "number")a.duration = 400;
            a.easing = Math[a.easing] || Math.easeInOutSine;
            a.curAnim = Highcharts.extend({}, d);
            for (g in d)i = new n(b, a, g), f = null, g === "d" ? (i.paths = c.init(b, b.d, d.d), i.toD = d.d, e = 0, f = 1) : b.attr ? e = b.attr(g) : (e = parseFloat(HighchartsAdapter._getStyle(b, g)) || 0, g !== "opacity" && (h = "px")), f || (f = parseFloat(d[g])), i.custom(e, f, h)
        }
    }, _getStyle: function (c, b) {
        return window.getComputedStyle(c).getPropertyValue(b)
    }, getScript: function (c, b) {
        var d = l.getElementsByTagName("head")[0], a = l.createElement("script");
        a.type = "text/javascript";
        a.src = c;
        a.onload = b;
        d.appendChild(a)
    }, inArray: function (c, b) {
        return b.indexOf ? b.indexOf(c) : p.indexOf.call(b, c)
    }, adapterRun: function (c, b) {
        return parseInt(HighchartsAdapter._getStyle(c, b), 10)
    }, grep: function (c, b) {
        return p.filter.call(c, b)
    }, map: function (c, b) {
        for (var d = [], a = 0, e = c.length; a < e; a++)d[a] = b.call(c[a], c[a], a, c);
        return d
    }, offset: function (c) {
        for (var b = 0, d = 0; c;)b += c.offsetLeft, d += c.offsetTop, c = c.offsetParent;
        return{left: b, top: d}
    }, addEvent: function (c, b, d) {
        o(c).bind(b, d)
    }, removeEvent: function (c, b, d) {
        o(c).unbind(b, d)
    }, fireEvent: function (c, b, d, a) {
        var e;
        l.createEvent &&
            (c.dispatchEvent || c.fireEvent) ? (e = l.createEvent("Events"), e.initEvent(b, !0, !0), e.target = c, Highcharts.extend(e, d), c.dispatchEvent ? c.dispatchEvent(e) : c.fireEvent(b, e)) : c.HCExtended === !0 && (d = d || {}, c.trigger(b, d));
        d && d.defaultPrevented && (a = null);
        a && a(d)
    }, washMouseEvent: function (c) {
        return c
    }, stop: function (c) {
        c.stopAnimation = !0
    }, each: function (c, b) {
        return Array.prototype.forEach.call(c, b)
    }}
}();
