# Draws the footer buttons: three 88x31 badges and the orange XML chiclet.
# Pixel art at 1x, saved at 3x with hard edges so it stays crisp on phones.
import sys
from PIL import Image, ImageDraw, ImageFont
out = sys.argv[1]; berry_png = sys.argv[2]   # usage: python3 scripts/buttons.py buttons berry.png
B = '/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf'; R = '/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf'
f = lambda p, s: ImageFont.truetype(p, s)

def bevel(d, w, h, light, dark):
    d.line([(0, 0), (w - 1, 0)], fill=light); d.line([(0, 0), (0, h - 1)], fill=light)
    d.line([(0, h - 1), (w - 1, h - 1)], fill=dark); d.line([(w - 1, 0), (w - 1, h - 1)], fill=dark)

def new(w, h, bg):
    im = Image.new('RGB', (w, h), bg); d = ImageDraw.Draw(im); d.fontmode = '1'; return im, d
def ctext(d, x0, x1, y, s, font, fill):
    w = d.textlength(s, font=font); d.text((x0 + (x1 - x0 - w) // 2, y), s, font=font, fill=fill)
def save(im, name): im.resize((im.width * 3, im.height * 3), Image.NEAREST).save(f'{out}/{name}.png', optimize=True)

# 1. Best viewed with any browser
im, d = new(88, 31, (0, 0, 128)); bevel(d, 88, 31, (120, 120, 220), (0, 0, 60))
d.rectangle([1, 1, 86, 29], outline=(255, 204, 0))
ctext(d, 0, 88, 4, 'Best viewed with', f(R, 9), (255, 255, 255))
ctext(d, 0, 88, 16, 'ANY BROWSER', f(B, 9), (255, 204, 0))
save(im, 'any-browser')

# 2. Made with Notepad
im, d = new(88, 31, (192, 192, 192)); bevel(d, 88, 31, (255, 255, 255), (96, 96, 96))
d.rectangle([5, 5, 23, 25], fill=(255, 255, 255), outline=(0, 0, 0)); d.rectangle([5, 5, 23, 8], fill=(0, 0, 128), outline=(0, 0, 0))
for y in (12, 15, 18, 21): d.line([(8, y), (20 if y != 21 else 15, y)], fill=(90, 90, 90))
ctext(d, 26, 86, 4, 'Made with', f(R, 9), (0, 0, 0))
ctext(d, 26, 86, 15, 'NOTEPAD', f(B, 10), (0, 0, 128))
save(im, 'notepad')

# 3. The site's own button, with the berry
im, d = new(88, 31, (6, 10, 44)); bevel(d, 88, 31, (90, 110, 190), (0, 0, 20))
b = Image.open(berry_png).convert('RGB').resize((25, 25), Image.LANCZOS); im.paste(b, (3, 3)); d.rectangle([2, 2, 28, 28], outline=(255, 204, 0))
ctext(d, 30, 87, 5, 'Nostalgia', f(B, 9), (255, 255, 255))
ctext(d, 30, 87, 16, '.js', f(B, 10), (255, 204, 0))
save(im, 'nostalgiajs')

# 4. The orange XML chiclet
im, d = new(36, 14, (255, 102, 0)); bevel(d, 36, 14, (255, 190, 130), (120, 50, 0))
d.rectangle([1, 1, 34, 12], outline=(255, 255, 255))
ctext(d, 0, 36, 1, 'XML', f(B, 9), (255, 255, 255))
save(im, 'xml')

